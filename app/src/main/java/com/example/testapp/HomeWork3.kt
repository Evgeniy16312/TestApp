package com.example.testapp

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityHomeWork3Binding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

@DelicateCoroutinesApi
class HomeWork3 : AppCompatActivity() {

    private lateinit var binding: ActivityHomeWork3Binding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeWork3Binding.inflate(layoutInflater).also { setContentView(it.root) }
        setOnClickListeners()
    }

    @DelicateCoroutinesApi
    private fun setOnClickListeners() {
        binding.btnLoadImage.setOnClickListener {
            if (!(binding.etImageURL.text.isNullOrEmpty()) && URLUtil.isValidUrl(binding.etImageURL.text.toString())) {
                val url = binding.etImageURL.text.toString()
                binding.ivImage.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                loadImage(url)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.invalid_url_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @DelicateCoroutinesApi
    private fun loadImage(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onFailure(call: Call, e: IOException) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.ivImage.visibility = View.VISIBLE
                    binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_no_image))
                }
                Snackbar.make(
                    binding.root,
                    getString(R.string.retry_error),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.retry_btn)) { loadImage(url) }.show()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    val bitmap = BitmapFactory.decodeStream(response.body!!.byteStream())
                    GlobalScope.launch(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                        binding.ivImage.visibility = View.VISIBLE
                        binding.ivImage.setImageBitmap(bitmap)
                    }
                }
            }
        })
    }
}