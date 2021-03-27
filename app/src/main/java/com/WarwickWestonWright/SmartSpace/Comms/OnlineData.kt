package com.WarwickWestonWright.SmartSpace.Comms

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.SmartSpace.App.App
import com.WarwickWestonWright.SmartSpace.Constants.RPC_ACTION_GET_ROOMS_DETAIL
import com.WarwickWestonWright.SmartSpace.Constants.RPC_ACTION_GET_ROOMS_MASTER
import com.WarwickWestonWright.SmartSpace.Repos.IRoomDetailRepo
import com.WarwickWestonWright.SmartSpace.Repos.IRoomMasterRepo
import com.WarwickWestonWright.SmartSpace.Utilities.Base64Utilities
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringWriter
import java.lang.ref.WeakReference
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.zip.GZIPInputStream
import javax.net.ssl.*


class OnlineData(var callingActivity: AppCompatActivity): Runnable {
    private val onlineDataRef: WeakReference<OnlineData> = WeakReference(this)
    private val onlineDataHandler = OnlineDataHandler(onlineDataRef, callingActivity)
    private val app = App.getApp() as App
    private lateinit var url: URL
    private var verb: String? = null
    private var thread: Thread? = null
    private var methodName = ""

    private val allHostsValid = HostnameVerifier { hostname, session -> true }

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }

        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    })

    /*
    private val localTrustmanager: TrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }
    }
    private val trustAllCerts = arrayOf(localTrustmanager)
    * */

    fun getHttpsDataAsString(url: URL, verb: String) {
        methodName = "getHttpsDataAsString"
        this.url = url
        this.verb = verb?.toUpperCase()
        thread = Thread(this)
        thread?.start()
    }

    fun getHttpsImage(url: URL, verb: String) {
        methodName = "getHttpsImage"
        this.url = url
        this.verb = verb?.toUpperCase()
        thread = Thread(this)
        thread?.start()
    }

    override fun run() {
        if(methodName == "getHttpsDataAsString") {
            getHttpsDataAsString()
        }
        else if(methodName == "getHttpsImage") {
            getHttpsImage()
        }
    }

    private fun getHttpsDataAsString() {
        var httpsURLConnection: HttpsURLConnection? = null
        val msg: Message = Message()
        val bufferedReader: BufferedReader
        val sb: StringBuilder
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
            httpsURLConnection = url?.openConnection() as HttpsURLConnection
            httpsURLConnection.sslSocketFactory = sc!!.socketFactory
            httpsURLConnection.hostnameVerifier = allHostsValid
            if(verb?.toUpperCase() == "POST") {
                httpsURLConnection?.doInput = true
                httpsURLConnection?.doOutput = true
                httpsURLConnection?.useCaches = true
            }
            else if(verb?.toUpperCase() == "GET") {
                httpsURLConnection?.doInput = true
                httpsURLConnection?.doOutput = false//?
                httpsURLConnection?.useCaches = true
            }
            httpsURLConnection?.requestMethod = verb
            httpsURLConnection?.readTimeout = 15 * 1000
            httpsURLConnection?.setRequestProperty("Accept-Encoding", "x-www-form-urlencoded")
            httpsURLConnection?.setRequestProperty("Accept-Encoding", "json")
            httpsURLConnection?.setRequestProperty("Accept-Charset", "UTF-8")
            httpsURLConnection?.setRequestProperty("Accept-Encoding", "gzip")
            httpsURLConnection?.connect()
            //bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection?.inputStream))
            bufferedReader = BufferedReader(InputStreamReader(GZIPInputStream(httpsURLConnection?.inputStream)))
            sb = StringBuilder()
            var line: String?
            bufferedReader.useLines { lines ->
                lines.forEach { line ->
                    sb.append(line)
                }
            }
            bufferedReader.close()
            httpsURLConnection?.inputStream?.close()
            msg.obj = sb.toString()
            onlineDataHandler.handleMessage(msg)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection?.disconnect()
            }
        }
    }

    private fun getHttpsImage() {
        val msg: Message = Message()
        val base64Utilities = Base64Utilities()
        val bitmap = base64Utilities.base64ImageFromSecureURL(url, "POST")
        msg.obj = bitmap
        onlineDataHandler.handleMessage(msg)
    }

    private fun minifyJSon(jsonStr: String) : String {
        val out = StringWriter()

        val factory = JsonFactory()
        val parser: JsonParser = factory.createParser(jsonStr)
        factory.createGenerator(out).use { gen ->
            while (parser.nextToken() != null) {
                gen.copyCurrentEvent(parser)
            }
        }
        return out.buffer.toString()
    }

    companion object {

        class OnlineDataHandler(
            private var onlineDataRef: WeakReference<OnlineData>,
            var callingActivity: AppCompatActivity
        ) : Handler(callingActivity.mainLooper) {
            override fun handleMessage(msg: Message) {
                val oldRef: OnlineData? = onlineDataRef.get()
                if(oldRef != null) {
                    oldRef.thread = null
                    Looper.prepare()
                    post {
                        if(oldRef.app.getRpcAction() == RPC_ACTION_GET_ROOMS_MASTER) {
                            if(oldRef.callingActivity::class.simpleName == "MainActivity") {
                                val iRoomMasterRepo = callingActivity as IRoomMasterRepo
                                val jsonStr = oldRef.minifyJSon(msg.obj as String)
                                iRoomMasterRepo.roomMasterRepoCallBack(jsonStr)
                            }
                        }
                        if(oldRef.app.getRpcAction() == RPC_ACTION_GET_ROOMS_DETAIL) {
                            if(oldRef.callingActivity::class.simpleName == "MainActivity") {
                                val iRoomDetailRepo = callingActivity as IRoomDetailRepo
                                val jsonStr = oldRef.minifyJSon(msg.obj as String)
                                iRoomDetailRepo.roomDetailRepoCallBack(jsonStr)
                            }
                        }
                    }
                    Looper.loop()
                }
            }
        }

    }
}