package com.raonsoft.matmanagement.screen.productManagement

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.*
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NfcF
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.databinding.ActivityProductManagementBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.dialog.DisburseDialog
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class ProductManagementActivity :
    BaseActivity<ProductManagementViewModel, ActivityProductManagementBinding>(),
    NfcAdapter.ReaderCallback {

    private var scannedProductIdx: Int? = null

    override val viewModel: ProductManagementViewModel by viewModels()

    private val techListsArray by lazy {
        arrayOf(arrayOf<String>(NfcF::class.java.name))
    }

    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    private lateinit var intentFiltersArray: Array<IntentFilter>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )

    }

    override fun onResume() {
        super.onResume()

        val options = Bundle()
        // Work around for some broken Nfc firmware implementations that poll the card too fast
        // Work around for some broken Nfc firmware implementations that poll the card too fast
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

        // Enable ReaderMode for all types of card and disable platform sounds

        // Enable ReaderMode for all types of card and disable platform sounds
        nfcAdapter.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NFC_BARCODE or
                    NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
            options
        )

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")    /* Handles all MIME based dispatches.
                                     You should specify only the ones that you need. */
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)

        nfcAdapter.enableForegroundDispatch(
            this,
            nfcPendingIntent,
            intentFiltersArray,
            techListsArray
        )


    }

    override fun onPause() {
        super.onPause()

        nfcAdapter.disableReaderMode(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val detectedTag: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        val message: NdefMessage = createTagMessage("")
        detectedTag?.let {
            writeTag(message, it)
        }

    }

    private fun createTagMessage(msg: String): NdefMessage {
        return NdefMessage(NdefRecord.createUri(msg))
    }

    override fun getViewBinding(): ActivityProductManagementBinding =
        ActivityProductManagementBinding.inflate(layoutInflater)

    override fun observeData() = with(binding) {

        viewModel.disburseState.observe(this@ProductManagementActivity) {
            it?.let { result ->
                if (result > 0) {
                    showToast("자재 불출에 성공하였습니다.")
                    productGroup.isVisible = false
                    readyScan.isVisible = true
                    scannedProductIdx = null
                } else {
                    showToast("자재 불출에 실패하였습니다.")
                }
            }
        }

        /*viewModel.scannedItemIdx.observe(this@ProductManagementActivity) {
            scannedProductIdx = it
        }*/

        viewModel.productInfo.observe(this@ProductManagementActivity) {
            it?.let { result ->
                /*scannedProductIdx = result["product_idx"]?.let { productIdx ->
                    productIdx
                }*/

                scannedProductIdx = result["product_idx"]?.toString()?.toDouble()?.toInt()

                scannedProductIdx?.let { productIdx ->
                    if (productIdx > 0) {
                        val productName = result["product_name"]?.toString()
                        val itemImage = result["item_image"]?.toString()

                        productGroup.isVisible = true
                        readyScan.isVisible = false

                        Glide.with(productImv.context)
                            .load(DefaultUrl.DEFAULT_IMAGE_URL + itemImage)
                            .error(R.drawable.img6)
                            .circleCrop()
                            .into(productImv)

                        productNameTxv.text = productName

                    } else {
                        showToast("등록되지 않은 자재입니다.")
                        scannedProductIdx = null

                        productGroup.isVisible = false
                        readyScan.isVisible = true
                    }

                } ?: kotlin.run {

                    showToast("등록되지 않은 자재입니다.")
                    scannedProductIdx = null

                    productGroup.isVisible = false
                    readyScan.isVisible = true
                }

            }
        }

        viewModel.productReturnState.observe(this@ProductManagementActivity) {
            it?.let { result ->

                if (result > 0) {
                    showToast("자재 반납에 성공하였습니다.")
                    productGroup.isVisible = false
                    readyScan.isVisible = true
                    scannedProductIdx = null

                } else {
                    showToast("자재 반납에 실패하였습니다.")
                }

            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        toolbar.setNavigationOnClickListener {
            finish()
        }

        disburseButton.setOnClickListener {

            //company: String, platoon: String, name: String, etc: String
            scannedProductIdx?.let {

                DisburseDialog(this@ProductManagementActivity) { company, platoon, name, etc ->
                    disburse(company, platoon, name, etc)
                }.show()

            } ?: kotlin.run {

                showToast("NFC TAG를 스캔해주세요.")

            }

        }


        returnButton.setOnClickListener {

            scannedProductIdx?.let { productIdx ->

                if (productIdx > 0) {
                    viewModel.productReturn(productIdx)
                } else {
                    showToast("스캔된 NFC TAG가 올바르지 않습니다.")
                }

            } ?: kotlin.run {
                showToast("자재를 스캔해주세요.")
            }

        }

    }

    private fun showToast(msg: String) {
        Toast.makeText(this@ProductManagementActivity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun disburse(company: String, platoon: String, name: String, etc: String) {
        scannedProductIdx?.let { productIdx ->
            AccountInfoSingleton.account_idx?.let { accountIdx ->
                viewModel.disburse(productIdx, accountIdx, company, platoon, name, etc)
            }

        }

    }

    private fun writeTag(message: NdefMessage, tag: Tag) {
        Log.wtf("writeTag", "writeTag")
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(tag)
            Log.wtf("writeTag", "writeTag2")
            if (ndef != null) {
                Log.wtf("writeTag", "ndef != null")
                ndef.connect()
                if (!ndef.isWritable) {
                    Log.wtf("writeTag", "!ndef.isWritable")
                    Toast.makeText(applicationContext, "can not write NFC tag", Toast.LENGTH_SHORT)
                        .show()
                }
                if (ndef.maxSize < size) {
                    Log.wtf("writeTag", "ndef.maxSize < size")
                    Toast.makeText(applicationContext, "NFC tag size too large", Toast.LENGTH_SHORT)
                        .show()
                }
                ndef.writeNdefMessage(message)
                Toast.makeText(applicationContext, "NFC tag is writted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        val mNdef = Ndef.get(tag)

        if (mNdef != null) {

            val mNdefMessage = mNdef.cachedNdefMessage

            if (mNdefMessage != null && mNdefMessage.records != null) {

                val payload = String(mNdefMessage.records[0].payload)
                val productTag = payload.substring(3, payload.length)
                Log.wtf("productTag", productTag)


                if (payload.isNotEmpty()) {
                    viewModel.getProductInfo(productTag)
                } else {
                    runOnUiThread {
                        showToast("스캔된 NFC TAG가 올바르지 않습니다.")
                    }
                }

                Log.wtf("tag is ", payload)


            } else {

                runOnUiThread {
                    showToast("스캔된 NFC TAG가 올바르지 않습니다.")
                }

                Log.wtf("mNdefMessage.records", "mNdefMessage.records null")
            }

        }
    }


}