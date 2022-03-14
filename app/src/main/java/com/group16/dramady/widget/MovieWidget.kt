package com.group16.dramady.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.group16.dramady.R

/**
 * Implementation of App Widget functionality.
 */
class MovieWidget : AppWidgetProvider() {
    /*private val clickWidget = "android.appwidget.action.APPWIDGET_CLICK"

    fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, MovieWidget::class.java)
        Log.i("class: ", javaClass.toString())
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }*/

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            //val remoteViews = RemoteViews(context.packageName, R.layout.quotes_widget)
            //remoteViews.setOnClickPendingIntent(R.id.button_appwidget, getPendingSelfIntent(context, clickWidget))

            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        //https://stackoverflow.com/questions/14798073/button-click-event-for-android-widget
        /*
        if(intent.action == clickWidget){
            val views = RemoteViews(context.packageName, R.layout.quotes_widget)
            views.setTextViewText(R.id.appwidget_text, "Clicked")
            val componentName = ComponentName(context, AppWidgetProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, views)
            Log.i("button: ", "clicked!")
        }*/


    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
//    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.quotes_widget)
//    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}