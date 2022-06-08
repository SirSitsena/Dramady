package com.group16.dramady.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.group16.dramady.R
import com.group16.dramady.rest.result_type.Review

class ReviewsListAdapter(private val context: Context, private val dataSource: List<Review>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Review {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: inflater.inflate(R.layout.list_reviews, parent, false)

        val review = getItem(position)

        val reviewsContent = itemView.findViewById(R.id.reviews_list_content) as TextView
        reviewsContent.text = review.content

        return itemView
    }
}