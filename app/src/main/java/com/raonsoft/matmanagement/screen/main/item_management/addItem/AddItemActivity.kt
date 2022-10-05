package com.raonsoft.matmanagement.screen.main.item_management.addItem

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityAddItemBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class AddItemActivity : BaseActivity<AddItemViewModel, ActivityAddItemBinding>() {

    override val viewModel: AddItemViewModel by viewModels()

    private var saveFileName: String? = null
    private var saveFilePath: Uri? = null
    private var count = 0

    override fun getViewBinding(): ActivityAddItemBinding =
        ActivityAddItemBinding.inflate(layoutInflater)

    override fun observeData() {

        viewModel.uploadImageToFtpState.observe(this@AddItemActivity) {

            when (it) {
                0 -> {
                    count++
                    //이상게 2번 불러와짐 해서 count 달아놨음
                    if (count == 1) {
                        AccountInfoSingleton.account_idx?.let { accountIdx ->

                            viewModel.registerItem(
                                item_name = binding.battalionEdit.text.toString(),
                                item_image = saveFileName,
                                account_idx = accountIdx
                            )
                        }
                    }


                }
                else -> {
                    Log.wtf("AddItemActivity", "observeData upload Fail")
                    showToast("이미지 업로드에 실패하였습니다.")
                }
            }
        }

        viewModel.registerItem.observe(this@AddItemActivity) {
            it?.let {
                if (it > 0) {
                    showToast("품목 업로드에 성공하였습니다.")
                    finish()

                } else {
                    showToast("품목 업로드에 실패하였습니다.")
                }
            }
        }
    }


    override fun initViews() = with(binding) {
        super.initViews()

        editButton.setOnClickListener {
            validation()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        itemImv.setOnClickListener {
            permissionCheck()
        }
    }

    private fun validation() = with(binding) {
        if (battalionEdit.text.isEmpty()) {
            Toast.makeText(this@AddItemActivity, "품목명을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            saveFilePath?.let {
                viewModel.uploadImage(it.toString())
            } ?: kotlin.run {
                AccountInfoSingleton.account_idx?.let {
                    viewModel.registerItem(
                        item_name = battalionEdit.text.toString(),
                        item_image = null,
                        it
                    )
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@AddItemActivity, msg, Toast.LENGTH_SHORT).show()
    }


    private fun permissionCheck() {
        requestMultiplePermissions.launch(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGrant ->
            if (isGrant) {
                DefaultBottomSheetDialog(startButtonText = "갤러리", endButtonText = "카메라") {

                    if (it) {
                        requestGallery.launch(Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                        })
                    } else {
                        requestCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    }

                }.show(this@AddItemActivity.supportFragmentManager, "MediaDialog")
            }
        }


    private val requestCamera: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.wtf("result DATA", result.data.toString())
        if (result.data != null) {
            val bitmap: Bitmap = result.data?.extras?.get("data") as Bitmap
            Glide.with(binding.itemImv.context).load(bitmap).apply(RequestOptions().circleCrop())
                .error(R.drawable.img6).into(binding.itemImv)

            saveBitmapToJpeg(bitmap)
            getCacheImage()
        }

    }

    private val requestGallery: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val url: Uri? = result.data?.data
        /*Glide.with(requireContext())
            .load(url.toString())
            .apply(RequestOptions().circleCrop())
            .error(R.drawable.profile_sample)
            .into(profileImv)*/
        Log.wtf("result DATA", result.data?.data.toString())

        var bitmap: Bitmap? = null
        bitmap = url?.let {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                MediaStore.Images.Media.getBitmap(this@AddItemActivity.contentResolver, it)
            } else {
                /*val source = ImageDecoder.createSource(requireContext().contentResolver, it)
                ImageDecoder.decodeBitmap(source)*/

                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(this@AddItemActivity.contentResolver, it)
                ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
                    decoder.isMutableRequired = true
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                }
            }
        }

        Glide.with(binding.itemImv.context)
            .load(bitmap)
            .apply(RequestOptions().circleCrop())
            .error(R.drawable.img6)
            .into(binding.itemImv)

        bitmap?.let {
            saveBitmapToJpeg(bitmap = it)
            getCacheImage()
        }


    }

    private fun saveBitmapToJpeg(bitmap: Bitmap) {
        val storage = cacheDir
        saveFileName = System.currentTimeMillis().toString() + ".jpg"
        val tempFile = saveFileName?.let { File(storage, it) }
        try {
            tempFile?.createNewFile()
            val out = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun getCacheImage() {
        val file = File(cacheDir.toString())

        file.listFiles()?.let {
            for (tempFile in it) {
                if (saveFileName == tempFile.name) {
                    saveFilePath = tempFile.absolutePath.toUri()
                }
            }
        }

        /*saveFilePath?.let {
            viewModel.uploadImage(it.toString())
        }*/
    }


}