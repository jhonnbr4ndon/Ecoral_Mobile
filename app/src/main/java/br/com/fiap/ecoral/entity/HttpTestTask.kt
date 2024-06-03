package br.com.fiap.ecoral.entity

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpTestTask : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg urls: String): String {
        var result = ""
        try {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
                result = stringBuilder.toString()
            } else {
                result = "HTTP error code: $responseCode"
            }
        } catch (e: IOException) {
            Log.e("HttpTestTask", "Error: ${e.message}")
            result = "Error: ${e.message}"
        }
        return result
    }

    override fun onPostExecute(result: String) {
        Log.d("HttpTestTask", "Response: $result")
    }
}
