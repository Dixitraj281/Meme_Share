package com.project.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentimageurl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()

    }

    private fun loadmeme(){
        // Instantiate the RequestQueue.
        progressbar.visibility = View.VISIBLE
        currentimageurl = "https://meme-api.herokuapp.com/gimme"
        val queue = Volley.newRequestQueue(this)
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, currentimageurl, null,
            Response.Listener { response ->
                currentimageurl = response.getString("url")
                Glide.with(this).load(currentimageurl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }
                }).into(memeimage)
            }, Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }


    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this cool meme i got from Reddit $currentimageurl")
        val chooser = Intent.createChooser(intent,"share this meme with ...")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmeme()
    }
}