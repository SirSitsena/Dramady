package com.group16.dramady.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.group16.dramady.R
import com.group16.dramady.rest.result_type.Reviews
import com.squareup.picasso.Picasso

class ReviewsArrayAdapter(private val context: Context, private val dataSource: List<Reviews>): BaseAdapter() {
    private val inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
            return dataSource.size
        }

    override fun getItem(position: Int): Reviews {
        return dataSource.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflator.inflate(R.layout.list_reviews, parent, false)

        val review = getItem(position)

        val reviewsContent = rowView.findViewById(R.id.reviews_list_content) as TextView
        reviewsContent.text = review.content

        return rowView
    }
}