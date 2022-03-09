package com.group16.dramady.rest
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.group16.dramady.rest.result_type.MoviesList
import com.group16.dramady.rest.result_type.SearchMovie
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit


object ImdbManager {


    private const val API_KEY1 = "k_9t0l0iej"
    private const val API_KEY2 = "k_rs8s2dp4"
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    private val movieListAdapter = gson.getAdapter(MoviesList::class.java)
    private val searchResultAdapter = gson.getAdapter(SearchMovie::class.java)

    fun getPopularNowList(): List<MoviesList.Movie>? {   //MostPopularMovies
        val request = Request.Builder()
            .url("https://imdb-api.com/en/API/MostPopularMovies/$API_KEY2")
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
            .url("https://imdb-api.com/en/API/Top250Movies/$API_KEY2")
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

    fun getSearchMoviesByKeywords(keywords: String): SearchMovie? {
        val request = Request.Builder()
            .url("http://192.168.1.107:8000/api/movies/search/$keywords")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    //Log.i("test", response.body!!.string())
                    searchResultAdapter.fromJson(response.body!!.string())//.string())
                } else {
                    null
                }
            }
        } catch(e:IOException){
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }
}