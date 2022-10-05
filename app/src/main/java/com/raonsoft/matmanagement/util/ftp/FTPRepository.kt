package com.raonsoft.matmanagement.util.ftp

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class FTPRepository {
    private val ftpClient = FTPClient()

    fun uploadImage(filePath : String) : Result<Boolean> {
        Log.wtf("FTPRepository", "uploadImage")
        try {
            ftpClient.apply {
                controlEncoding = "UTF-8"
                connect("zzipbbong.cafe24.com", 21)
                login("zzipbbong", "admin1237!")
                setFileType(FTP.BINARY_FILE_TYPE)
                enterLocalPassiveMode()
                bufferSize = 5 * 1024 * 1024
                changeWorkingDirectory("/tomcat/webapps/imagefile/material_management")
            }

            val uploadFile = File(filePath)
            var fis : FileInputStream? = null
            fis = FileInputStream(uploadFile)
            val isSuccess = ftpClient.storeFile(uploadFile.name, fis)
            ftpClient.logout()
            ftpClient.disconnect()
            return if (isSuccess) {
                Log.wtf("FTPRepository", "isSuccess")
                Result.Success(true)

            }else {
                Log.wtf("FTPRepository", "Failed")
                Result.Failed(false)
            }

        }catch (e : Exception) {
            Log.wtf("FTPRepository", "Exception")
            e.printStackTrace()
            return Result.Error(e)
        }
    }
}