package com.group16.dramady.rest
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.group16.dramady.rest.result_type.Reviews
import com.group16.dramady.rest.result_type.SearchMovie
import com.group16.dramady.rest.result_type.SearchResultMovie
import com.group16.dramady.storage.entity.AllTimeBestMovies
import com.group16.dramady.storage.entity.PopularNowMovies
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.CookieManager
import java.util.concurrent.TimeUnit


object apiManager {

    private const val API_KEY_UNLIMITED_5000 = "k_9t0l0iej"
    private const val API_KEY2 = "k_rs8s2dp4"
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

//    private val movieListAdapter = gson.getAdapter(Class<List<PopularNowMovies>>)
    private val searchResultAdapter = gson.getAdapter(SearchMovie::class.java)
    private val searchMovieResultAdapter = gson.getAdapter(SearchResultMovie::class.java)
    private val reviewsResultAdapter = gson.getAdapter(Reviews::class.java)

    fun getPopularNowList(): List<PopularNowMovies>? {   //MostPopularMovies
        val request = Request.Builder()
            .url("http://65.108.61.241:8000/api/movies/trending")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if ( response.isSuccessful) {
                    gson.fromJson<List<PopularNowMovies>>(response.body!!.string())
                } else{
                    null
                }
            }
        } catch (e:IOException){    //UnknownHostException <------ The error
            Log.i(e::class.java.simpleName, e.message.toString())
        }
    return null
    }

    fun getAllTimeBestList(): List<AllTimeBestMovies>? {

        val request = Request.Builder()
            .url("http://65.108.61.241:8000/api/movies/top250")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if ( response.isSuccessful) {
                    gson.fromJson<List<AllTimeBestMovies>>(response.body!!.string())
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
            .url("http://65.108.61.241:8000/api/movies/search/$keywords")
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

    fun getMovieByTitleId(titleId: String): SearchResultMovie? {
        val request = Request.Builder()
            .url("http://65.108.61.241:8000/api/movies/id/$titleId")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if(response.isSuccessful) {
                    searchMovieResultAdapter.fromJson(response.body!!.string())
                } else {
                    null
                }
            }
        } catch(e:IOException){
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun getReviewsByTitleId(titleId: String): List<Reviews>? {
        val request = Request.Builder()
            .url("http://65.108.61.241:8000/api/reviews/byTitleId/$titleId")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if(response.isSuccessful) {
                    gson.fromJson<List<Reviews>>(response.body!!.string())
                    //reviewsResultAdapter.fromJson(response.body!!.string())
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