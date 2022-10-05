package com.raonsoft.matmanagement.screen.nfcWrite

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.net.Uri
import android.nfc.*
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcF
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raonsoft.matmanagement.databinding.ActivityNfcwriteBinding
import java.io.IOException
import java.lang.Exception as Exception1


class NFCWriteActivity
    : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var binding: ActivityNfcwriteBinding? = null

    private val techListsArray by lazy {
        arrayOf(arrayOf<String>(NfcF::class.java.name))
    }

    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    private lateinit var intentFiltersArray: Array<IntentFilter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNfcwriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initViews()
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

    override fun onResume() {
        super.onResume()

        val options = Bundle()

        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

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

    private fun initViews() = with(binding!!) {

        nfcAdapter = NfcAdapter.getDefaultAdapter(this@NFCWriteActivity)
        nfcPendingIntent = PendingIntent.getActivity(
            this@NFCWriteActivity,
            0,
            Intent(this@NFCWriteActivity, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )

        toolbar.setNavigationOnClickListener {
            finish()
        }

        exitButton.setOnClickListener {
            finish()
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        Log.wtf("onTagDiscovered", "hi")
        val mNdef = Ndef.get(tag)

        if (mNdef != null) {
            // Or if we want to write a Ndef message
            // Create a Ndef Record
            val editText = binding?.tagEdit?.text.toString()
            val mRecord = NdefRecord.createTextRecord("ko", editText)

            //채팅 플로우를

            // Add to a NdefMessage
            val mMsg = NdefMessage(mRecord)

            try {
                mNdef.connect()
                mNdef.writeNdefMessage(mMsg)

                // Success if got to here

                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Write to NFC Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } catch (e: FormatException) {
                e.printStackTrace()
                // if the NDEF Message to write is malformed
                e.printStackTrace()
            } catch (e: TagLostException) {
                // Tag went out of range before operations were complete
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
                // if there is an I/O failure, or the operation is cancelled
            } finally {
                // Be nice and try and close the tag to
                // Disable I/O operations to the tag from this TagTechnology object, and release resources.
                try {
                    mNdef.close();
                } catch (e: IOException) {
                    e.printStackTrace()
                    // if there is an I/O failure, or the operation is cancelled
                }
            }


        }

    }
}