package com.group16.dramady.rest
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.group16.dramady.rest.result_type.MoviesList
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit


object ImdbManager {

    private const val API_KEY = "k_9t0l0iej"
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    private val movieListAdapter = gson.getAdapter(MoviesList::class.java)

    fun getPopularNowList(): List<MoviesList.Movie>? {   //MostPopularMovies
        val request = Request.Builder()
            .url("https://imdb-api.com/en/API/MostPopularMovies/$API_KEY")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if ( response.isSuccessful) {
                    movieListAdapter.fromJson(response.body!!.string()).items
                } else{
                    null
                }
            }
        } catch (e:IOException){    //UnknownHostException <------
            Log.i(e::class.java.simpleName, e.message.toString())
        }
    return null
    }


    fun getAllTimeBestList(): List<MoviesList.Movie>? {

        val request = Request.Builder()
            .url("https://imdb-api.com/en/API/Top250Movies/$API_KEY")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if ( response.isSuccessful) {
                    movieListAdapter.fromJson(response.body!!.string()).items
                } else{
                    null
                }
            }
        } catch (e:IOException){    //UnknownHostException <------
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }


}