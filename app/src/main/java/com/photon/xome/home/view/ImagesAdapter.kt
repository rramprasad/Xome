package com.photon.xome.home.view

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.photon.xome.R
import com.photon.xome.home.data.FlickrImage
import com.squareup.picasso.Picasso


class ImagesAdapter(private val activity: FragmentActivity, private val imagesList: List<FlickrImage>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.image_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val imageData = imagesList[position]
        val imageURL = "http://farm${imageData.farm}.static.flickr.com/${imageData.server}/${imageData.id}_${imageData.secret}.jpg"
        val imageSize = getDeviceWidth()/3
        Picasso.get()
            .load(imageURL)
            .placeholder(R.drawable.ic_launcher_background)
            .resize(imageSize,imageSize)
            .into(viewHolder.flickrImageView)
        viewHolder.titleTextView.text = imageData.title
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val flickrImageView: ImageView
        val titleTextView: TextView
        init {
            flickrImageView = itemView.findViewById(R.id.flickr_image_view)
            titleTextView  = itemView.findViewById(R.id.image_title_textview)
        }
    }

    override fun getItemId(position: Int): Long {
        if(imagesList.size > 0){
            return imagesList.get(position).id.toLong()
        }
        else{
            return super.getItemId(position)
        }
    }

    fun getDeviceWidth(): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

}
