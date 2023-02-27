package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService : AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        getRequestWithParameter()
    }

    private fun getRequestWithParameter(){
        val tvItem = findViewById<TextView>(R.id.tvItem)
        val responseLiveData:LiveData<Response<Album>> = liveData{
            val response : Response<Album> = retService.getAlbumByUserId(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if(albumList!=null){
                while (albumList.hasNext()){
                    val albumItem = albumList.next()
                    val rs = " "+"Album id: ${albumItem.id} "+"\n"+
                            " "+"Album title: ${albumItem.title} "+"\n"+
                            " "+"Album user id: ${albumItem.userId} "+"\n\n\n"
                    tvItem.append(rs)
                }
            }
        })
    }

    private fun getRequestWithPathParameter(){
        //path parameter demo
        val pathResponse:LiveData<Response<AlbumItem>> = liveData{
            val responseAlbumItem : Response<AlbumItem> = retService.getAlbumById(3)
            emit(responseAlbumItem)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })
    }
}