package com.WarwickWestonWright.SmartSpace.Utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.*
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class Base64Utilities {

    lateinit var bitmap: Bitmap
    lateinit var inputStream: InputStream
    lateinit var out: BufferedOutputStream
    val IO_BUFFER_SIZE = 64
    lateinit var myConn: HttpsURLConnection

    private val allHostsValid = HostnameVerifier { hostname, session -> true }

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }

        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    })

    fun base64ImageFromSecureURL(url: URL, verb: String): Bitmap {
        var sc: SSLContext? = null
        try {
            sc = SSLContext.getInstance("TLSv1.2")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        try {
            sc?.init(null, trustAllCerts, SecureRandom())
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        try {
            myConn = url.openConnection() as HttpsURLConnection
            myConn.sslSocketFactory = sc!!.socketFactory
            myConn.hostnameVerifier = allHostsValid
            myConn.requestMethod = verb.toUpperCase()
            inputStream = myConn.inputStream
            val dataStream = ByteArrayOutputStream()
            out = BufferedOutputStream(dataStream, IO_BUFFER_SIZE)
            copyCompletely(inputStream, out)
            val data = dataStream.toByteArray()
            val options = BitmapFactory.Options()
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.size, options)
        }
        catch (e: IOException) {
            Log.e("TAG", "Could not load Bitmap from: $url")
        }
        finally {
            try {
                inputStream?.close()
            }
            catch (e: IOException) {
                e.printStackTrace() //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                out?.close()
            }
            catch (e: IOException) {
                e.printStackTrace() //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return bitmap
    }

    //Method Not Fully Tested
    fun base64ImageFromURL(url: URL, verb: String): Bitmap {
        try {
            myConn = url.openConnection() as HttpsURLConnection
            inputStream = myConn.inputStream
            val dataStream = ByteArrayOutputStream()
            out = BufferedOutputStream(dataStream, IO_BUFFER_SIZE)
            copyCompletely(inputStream, out)
            val data = dataStream.toByteArray()
            val options = BitmapFactory.Options()
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.size, options)
        }
        catch (e: IOException) {
            Log.e("TAG", "Could not load Bitmap from: $url")
        }
        finally {
            try {
                inputStream?.close()
            }
            catch (e: IOException) {
                e.printStackTrace() //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                out?.close()
            }
            catch (e: IOException) {
                e.printStackTrace() //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return bitmap
    }

    @Throws(IOException::class)
    private fun copyCompletely(input: InputStream?, output: OutputStream) {
        if (output is FileOutputStream && input is FileInputStream) {
            try {
                val target = output.channel
                val source = input.channel
                source.transferTo(0, Int.MAX_VALUE.toLong(), target)
                source.close()
                target.close()
                return
            }
            catch (e: Exception) { /* failover to byte stream version */ }
        }
        val buf = ByteArray(8192)
        while (true) {
            val length = input!!.read(buf)
            if(length < 0) {
                break
            }
            output.write(buf, 0, length)
        }
        try {
            input.close()
        }
        catch (ignore: IOException) {}
        try {
            output.close()
        }
        catch (ignore: IOException) {}
    }

    fun convertToBase64(bitmap: Bitmap): String {
        val os = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        val byteArray = os.toByteArray()
        return Base64.encodeToString(byteArray, 0)
    }

    fun convertToBitmap(base64String: String): Bitmap {
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

}