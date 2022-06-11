package com.group16.dramady.rest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.group16.dramady.rest.result_type.Review
import com.group16.dramady.rest.result_type.FoundMovies
import com.group16.dramady.rest.result_type.SearchResultMovie
import com.group16.dramady.storage.entity.AllTimeBestMovie
import com.group16.dramady.storage.entity.PopularNowMovie
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit


object apiManager {
    private const val HOST = "65.108.61.241:8000"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)

    private val searchResultAdapter = gson.getAdapter(FoundMovies::class.java)
    private val searchMovieResultAdapter = gson.getAdapter(SearchResultMovie::class.java)


    fun getPopularNowList(): List<PopularNowMovie>? {
        val request = Request.Builder()
            .url("http://$HOST/api/movies/trending")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    gson.fromJson<List<PopularNowMovie>>(response.body!!.string())
                } else {
                    null
                }
            }
        } catch (e: IOException) {    //UnknownHostException <------ The error
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun getAllTimeBestList(): List<AllTimeBestMovie>? {

        val request = Request.Builder()
            .url("http://$HOST/api/movies/top250")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    gson.fromJson<List<AllTimeBestMovie>>(response.body!!.string())
                } else {
                    null
                }
            }
        } catch (e: IOException) {    //UnknownHostException <------
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun getFoundMoviesByPhrase(phrase: String): FoundMovies? {
        val escapedPhrase = phrase.replace(Regex("[^\\w ]*"), "")
        val request = Request.Builder()
            .url("http://$HOST/api/movies/search/$escapedPhrase")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    searchResultAdapter.fromJson(response.body!!.string())//.string())
                } else {
                    null
                }
            }
        } catch (e: IOException) {
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun getMovieByTitleId(titleId: String): SearchResultMovie? {
        val request = Request.Builder()
            .url("http://$HOST/api/movies/id/$titleId")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    searchMovieResultAdapter.fromJson(response.body!!.string())
                } else {
                    null
                }
            }
        } catch (e: IOException) {
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun getReviewsByTitleId(titleId: String): List<Review>? {
        val request = Request.Builder()
            .url("http://$HOST/api/reviews/byTitleId/$titleId")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    gson.fromJson<List<Review>>(response.body!!.string())
                } else {
                    null
                }
            }
        } catch (e: IOException) {
            Log.i(e::class.java.simpleName, e.message.toString())
        }
        return null
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}