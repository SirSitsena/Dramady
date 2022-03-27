package com.group16.dramady

import android.util.Log
import com.group16.dramady.rest.apiManager
import com.group16.dramady.storage.MovieRoomDatabase

object Updater {

    suspend fun updatePopularNow(){
        val movieDao = MovieRoomDatabase.getPopularNowDao()!!

        val popularNowList = apiManager.getPopularNowList()

        if( popularNowList != null ){
            if(movieDao.count() != 0){
                Log.i("Error", "!=0")
                movieDao.deleteAll()
            }
            Log.i("Error", "!=null")
            for ( movieApi in popularNowList ){
                movieDao.insert( movieApi)
            }
        }
    }

    suspend fun updateAllTimeBest(): Boolean{
        val movieDao = MovieRoomDatabase.getAllTimeBestDao()!!

        val allTimeBestList = apiManager.getAllTimeBestList()

        if( allTimeBestList != null ){
            if(movieDao.count() != 0){
                Log.i("Error", "!=0")
                movieDao.deleteAll()
            }
            Log.i("Error", "!=null")
            for ( movieApi in allTimeBestList ){
                movieDao.insert( movieApi)
            }
            return true
        } else {
            return false
        }
    }
}