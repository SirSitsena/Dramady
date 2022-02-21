package com.group16.dramady

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.group16.dramady.databinding.ActivityMainBinding
import com.group16.dramady.rest.ImdbManager
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.Movie
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
//    val database by lazy { MovieRoomDatabase.getDatabase(this, applicationScope) }
//    val repository by lazy { MovieRepository(database.movieDao()) } //---> Do we need to implement that? \Anestis


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.search_page, R.id.popular_now, R.id.all_time_best, R.id.watch_list, R.id.favorite_list
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Initializes the database object
        val database = MovieRoomDatabase.getDatabase(this, applicationScope)

        var movieDao = database.movieDao()

        applicationScope.launch(Dispatchers.IO){ // Separate in a new Class for Database Updates
            val popularNowList = ImdbManager.getPopularNowList()
//            val allTimeBestList = ImdbManager.getAllTimeBestList()

            if( popularNowList != null ){
                if(movieDao.count() != 0){
                    Log.i("Error", "!=0")
                    movieDao.deleteAll()
                }
                Log.i("Error", "!=null")
                for ( movieApi in popularNowList ){
                    movieDao.insert( Movie(
                        movieApi.id,
                        movieApi.title,
                        movieApi.fullTitle,
                        "",
                        movieApi.image,
                        movieApi.year,
                        movieApi.crew,
                        movieApi.imDbRating,
                        movieApi.rank,
                        movieApi.rankUpDown,
                        movieApi.imDbRatingCount,
                        -1
                    )
                    )
                }
            }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}