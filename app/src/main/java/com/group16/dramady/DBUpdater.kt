package com.group16.dramady

import com.group16.dramady.rest.apiManager
import com.group16.dramady.storage.DramadyRoomDatabase

object DBUpdater {

    suspend fun updatePopularNow() {
        val movieDao = DramadyRoomDatabase.getPopularNowDao()!!

        val popularNowList = apiManager.getPopularNowList()

        if (popularNowList != null) {
            movieDao.deleteAll()
            for (movie in popularNowList) {
                movieDao.insert(movie)
            }
        }
    }

    suspend fun updateAllTimeBest(){
        val movieDao = DramadyRoomDatabase.getAllTimeBestDao()!!

        val allTimeBestList = apiManager.getAllTimeBestList()

        if (allTimeBestList != null) {
            movieDao.deleteAll()
            for (movie in allTimeBestList) {
                movieDao.insert(movie)
            }
        }
    }
}