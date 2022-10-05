package com.raonsoft.matmanagement.screen.main.setting.changeInfo

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.data.url.DefaultUrl.DEFAULT_IMAGE_URL
import com.raonsoft.matmanagement.databinding.FragmentChangeInfoBinding
import com.raonsoft.matmanagement.extention.load
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ChangeInfoFragment : BaseFragment<ChangeInfoViewModel, FragmentChangeInfoBinding>() {

    private var armyunit_idx: Int? = null

    private var account_profileName: String? = null
    private var saveFilePath: Uri? = null
    private var count = 0

    override val viewModel: ChangeInfoViewModel by viewModels()

    override fun getViewBinding(): FragmentChangeInfoBinding =
        FragmentChangeInfoBinding.inflate(layoutInflater)

    override fun observeData() {

        viewModel.getAccountDetailInfoLiveData.observe(this@ChangeInfoFragment) {
            it?.let {
                initData(it)
            }
        }

        viewModel.changeInfo.observe(this@ChangeInfoFragment) {
            it?.let {
                if (it > 0) {
                    showToast("정보변경에 성공하였습니다.")
                    findNavController().popBackStack()
                } else {
                    showToast("정보변경에 실패하였습니다.")
                }
            } ?: kotlin.run {
                showToast("정보변경에 실패하였습니다.")
            }
        }

        viewModel.uploadImageToFtpState.observe(this@ChangeInfoFragment) {

            when (it) {
                0 -> {
                    count++
                    //이상게 2번 불러와짐 해서 count 달아놨음
                    if (count == 1) {
                        changeInfo()
                    }
                }

                else -> {
                    Log.wtf("AddItemActivity", "observeData upload Fail")
                    showToast("이미지 업로드에 실패하였습니다.")
                    if (count == 1) {
                        changeInfo()
                    }
                }
            }

        }
    }

    private fun initData(map: HashMap<String, Any>) = with(binding) {
        armyunit_idx = map["armyunit_idx"].toString().toDouble().toInt()
        armyUnitNameEdit.setText(map["armyunit_name"].toString())
        divisionEdit.setText(map["armyunit_battalion"].toString())
        regimentEdit.setText(map["armyunit_division"].toString())
        battalionEdit.setText(map["armyunit_regiment"].toString())
    }

    override fun initViews() = with(binding) {
        super.initViews()

        AccountInfoSingleton.account_idx?.let {
            viewModel.getAccountDetailInto(it)
            account_profileName = AccountInfoSingleton.account_profile
        } ?: kotlin.run {
            showToast("계정 정보조회에 실패하였습니다.")
            findNavController().popBackStack()
        }


        editButton.setOnClickListener {
            validation()
        }

        profileImb.apply {

            Glide.with(context)
                .load(DEFAULT_IMAGE_URL + AccountInfoSingleton.account_profile)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.ic_default_profile).into(this)

            setOnClickListener {
                permissionCheck()
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


    }


    private fun validation() = with(binding) {
        when {

            pwEdit.text.toString().length <= 7 -> {
                showToast("비밀번호는 8자 이상등록해주세요.")
            }

            pwEdit.text.toString() != pwConfirmEdit.text.toString() -> {
                showToast("비밀번호가 일치하지 않습니다.")
            }

            armyUnitNameEdit.text.toString().isEmpty() -> {
                showToast("부대명을 입력해주세요.")
            }

            divisionEdit.text.toString().isEmpty() -> {
                showToast("소속사단을 입력해주세요.")
            }

            regimentEdit.text.toString().isEmpty() -> {
                showToast("소속연대를 입력해주세요.")
            }

            battalionEdit.text.toString().isEmpty() -> {
                showToast("소속대대를 입력해주세요.")
            }

            else -> {

                if (account_profileName == AccountInfoSingleton.account_profile) {
                    changeInfo()
                } else {
                    saveFilePath?.let {
                        viewModel.uploadImage(imagePath = it.toString())
                    }
                }
            }

        }
    }

    private fun changeInfo() = with(binding) {
        AccountInfoSingleton.account_idx?.let {
            viewModel.changeInfo(
                account_idx = it,
                account_password = pwEdit.text.toString(),
                account_name = armyUnitNameEdit.text.toString(),
                account_profile = account_profileName,
                armyunit_name = armyUnitNameEdit.text.toString(),
                armyunit_division = divisionEdit.text.toString(),
                armyunit_regiment = regimentEdit.text.toString(),
                armyunit_battalion = battalionEdit.text.toString()
            )
        }
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

                }.show(requireActivity().supportFragmentManager, "MediaDialog")
            }
        }


    private val requestCamera: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.wtf("result DATA", result.data.toString())
        if (result.data != null) {
            val bitmap: Bitmap = result.data?.extras?.get("data") as Bitmap
            Glide.with(binding.profileImb.context).load(bitmap).apply(RequestOptions().circleCrop())
                .error(R.drawable.img6).into(binding.profileImb)

            saveBitmapToJpeg(bitmap)
            getCacheImage()
        } else {
            Glide.with(binding.profileImb.context)
                .load(DEFAULT_IMAGE_URL + AccountInfoSingleton.account_profile)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.img6).into(binding.profileImb)

            saveFilePath = null
            account_profileName = AccountInfoSingleton.account_profile
        }

    }

    private val requestGallery: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val url: Uri? = result.data?.data
        if (url != null) {
            var bitmap: Bitmap? = null
            bitmap = url.let {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
                } else {
                    /*val source = ImageDecoder.createSource(requireContext().contentResolver, it)
                            ImageDecoder.decodeBitmap(source)*/

                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(requireActivity().contentResolver, it)
                    ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
                        decoder.isMutableRequired = true
                        decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    }
                }
            }

            Glide.with(binding.profileImb.context)
                .load(bitmap)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.img6)
                .into(binding.profileImb)

            bitmap?.let {
                saveBitmapToJpeg(bitmap = it)
                getCacheImage()
            }

        } else {

            Glide.with(binding.profileImb.context)
                .load(DEFAULT_IMAGE_URL + AccountInfoSingleton.account_profile)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.img6).into(binding.profileImb)

            saveFilePath = null
            account_profileName = AccountInfoSingleton.account_profile
        }

        Log.wtf("result DATA", result.data?.data.toString())

    }

    private fun saveBitmapToJpeg(bitmap: Bitmap) {
        val storage = requireActivity().cacheDir
        account_profileName = System.currentTimeMillis().toString() + ".jpg"
        val tempFile = account_profileName?.let { File(storage, it) }
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
        val file = File(requireActivity().cacheDir.toString())

        file.listFiles()?.let {
            for (tempFile in it) {
                if (account_profileName == tempFile.name) {
                    saveFilePath = tempFile.absolutePath.toUri()
                }
            }
        }

        /*saveFilePath?.let {
            viewModel.uploadImage(it.toString())
        }*/
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


}