package com.matthew.carvalhodagenais.gamecubecollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
import kotlinx.android.synthetic.main.console_item.view.*

class ConsoleListRecyclerAdapter: ListAdapter<Console, ConsoleListRecyclerAdapter.ConsoleHolder>(
    DIFF_CALLBACK) {

    private var listener: ItemOnClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Console>() {
            override fun areContentsTheSame(oldItem: Console, newItem: Console): Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.description == oldItem.description &&
                        oldItem.regionId == newItem.regionId &&
                        oldItem.conditionId == newItem.conditionId &&
                        oldItem.imageName == newItem.imageName)
            }
            override fun areItemsTheSame(oldItem: Console, newItem: Console): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: ConsoleHolder, position: Int) {
        val console = getItem(position)
        holder.titleTextView.text = console.title
        if (console.description != "" && console.description != null) {
            holder.descriptionTextView.text = console.description
        }

        // Place appropriate cover art
        if (console.imageName != "" && console.imageName != null) {
            Glide
                .with(holder.parentView)
                .load(ImageStorageHelper.getImageWithPath(console.imageName.toString()))
                .into(holder.imageView)
        } else {
            Glide
                .with(holder.parentView)
                .load(holder.parentView.context.getDrawable(R.drawable.no_console))
                .into(holder.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsoleHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.console_item, parent, false)
        return ConsoleHolder(itemView)
    }

    fun setItemOnClickListener(listener: ItemOnClickListener) {
        this.listener = listener
    }

    inner class ConsoleHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position: Int = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var parentView = view
        var titleTextView = view.title_text_view
        var descriptionTextView = view.description_text_view
        var imageView = view.console_image_view
    }

    interface ItemOnClickListener {
        fun onItemClick(console: Console)
    }
}