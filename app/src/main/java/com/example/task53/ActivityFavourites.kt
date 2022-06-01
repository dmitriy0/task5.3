package com.example.task53

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ActivityFavourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val data = ArrayList<Cat>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = Adapter(data)
        recyclerView.adapter = adapter

        val client = HttpClient(OkHttp)
        GlobalScope.launch(Dispatchers.IO) {
            val responseVotes: String = client.get("https://api.thecatapi.com/v1/votes") {
                headers {
                    append("x-api-key", "e7e933f7-09f6-43e3-a68a-b8e30c70e434")
                }
                parameter("sub_id", "something_id")
            }

            val votes = Json{ignoreUnknownKeys = true}.decodeFromString<List<Vote>>(responseVotes)
            for (i in votes){
                if (i.value == 1){
                    val responseImage: String = client.get("https://api.thecatapi.com/v1/images/${i.image_id}") {
                        headers {
                            append("x-api-key", "e7e933f7-09f6-43e3-a68a-b8e30c70e434")
                        }
                    }
                    val catImage = Json{ignoreUnknownKeys = true}.decodeFromString<Cat>(responseImage)
                    data.add(catImage)
                    this@ActivityFavourites.runOnUiThread{
                        adapter.notifyItemInserted(data.size-1)
                    }
                }


            }

        }
    }
}