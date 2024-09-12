package com.example.cse226_ca1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUrl: EditText
    private lateinit var buttonSearch: Button
    private lateinit var textViewError: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUrl = findViewById(R.id.editTextUrl)
        buttonSearch = findViewById(R.id.buttonSearch)
        textViewError = findViewById(R.id.textViewError)
        imageView = findViewById(R.id.imageView)

        buttonSearch.setOnClickListener {
            val url = editTextUrl.text.toString()

            textViewError.visibility = TextView.GONE
            textViewError.text = ""

            if (url.isNotEmpty()) {
                downloadAndShowImage(url)
            } else {
                textViewError.visibility = TextView.VISIBLE
                textViewError.text = "Please enter a valid URL."
            }
        }
    }

    private fun downloadAndShowImage(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.downloadImage(url)
                if (response.isSuccessful) {
                    val responseBody: ResponseBody = response.body()!!
                    val inputStream: InputStream = responseBody.byteStream()
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        textViewError.visibility = TextView.VISIBLE
                        textViewError.text = "Failed to load: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    textViewError.visibility = TextView.VISIBLE
                    textViewError.text = "Failed to load content: ${e.localizedMessage}"
                }
            }
        }
    }
}
