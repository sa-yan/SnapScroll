package com.sayan.imageapp

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var searchEdit:EditText
    private lateinit var btnSearch:Button
    private lateinit var imageRecycler:RecyclerView
    private lateinit var queue:RequestQueue;
    private lateinit var imageUrlList:ArrayList<String>

    var totalImage = 0



    private val CLIENT_ID = "wiB8UpLc0ir0dLor_FkYlBH7jplH-pf8LtH3n3d2BxU"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchEdit = findViewById(R.id.searchImage)
        btnSearch=findViewById(R.id.buttonSearch)
        imageRecycler=findViewById(R.id.imageRecyclerView)

        imageUrlList = ArrayList<String>();

        queue= Volley.newRequestQueue(this)


        btnSearch.setOnClickListener {
            val type = searchEdit.text.toString()
            getImagesByApi(type)

        }

        imageRecycler.layoutManager=GridLayoutManager(this, 2)
        imageRecycler.setHasFixedSize(true)
        imageRecycler.adapter=ImageAdapter(this, imageUrlList)

    }

    private fun getImagesByApi(type:String) {
        imageUrlList.clear()
        val url = "https://api.unsplash.com/search/photos/?per_page=${30}&query=${type}&client_id=${CLIENT_ID}"
        val jsonObjectRequest = JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
            { response ->
                totalImage = response.getInt("total")
                val jsonArray = response.getJSONArray("results")
                for (i in 0 until jsonArray.length()){
                    val urlsObjects = jsonArray.getJSONObject(i).getJSONObject("urls")
                    val regularImage = urlsObjects.getString("regular")
                    imageUrlList.add(regularImage)
                    Log.d("Response",totalImage.toString())
                }

                imageRecycler.adapter?.notifyDataSetChanged()
            },
            { error ->
                Log.d("Response",error.toString())
            }
        )

        queue.add(jsonObjectRequest)

    }

}