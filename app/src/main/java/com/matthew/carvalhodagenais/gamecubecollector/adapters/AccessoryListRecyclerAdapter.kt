package com.matthew.carvalhodagenais.gamecubecollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
import kotlinx.android.synthetic.main.accessory_item.view.*

class AccessoryListRecyclerAdapter: ListAdapter<Accessory, AccessoryListRecyclerAdapter.AccessoryHolder> (
    DIFF_CALLBACK
) {
    private var listener: ItemOnClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Accessory>() {
            override fun areContentsTheSame(oldItem: Accessory, newItem: Accessory): Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.description == oldItem.description &&
                        oldItem.conditionId == newItem.conditionId &&
                        oldItem.imageName == newItem.imageName)
            }
            override fun areItemsTheSame(oldItem: Accessory, newItem: Accessory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: AccessoryHolder, position: Int) {
        val accessory = getItem(position)
        holder.titleTextView.text = accessory.title
        if (accessory.description != null && accessory.description != "") {
            holder.descriptionTextView.text = accessory.description
        }

        // Place appropriate accessory image
        if (accessory.imageName != "" && accessory.imageName != null) {
            Glide
                .with(holder.parentView)
                .load(ImageStorageHelper.getImageWithPath(accessory.imageName.toString()))
                .into(holder.imageView)
        } else {
            Glide
                .with(holder.parentView)
                .load(holder.parentView.context.getDrawable(R.drawable.no_accessory))
                .into(holder.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoryHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.accessory_item, parent, false)
        return AccessoryHolder(itemView)
    }

    fun setItemOnClickListener(listener: ItemOnClickListener) {
        this.listener = listener
    }

    fun getAccessory(position: Int): Accessory {
        return getItem(position)
    }

    inner class AccessoryHolder(view: View): RecyclerView.ViewHolder(view) {
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
        var imageView = view.accessory_image_view
    }

    interface ItemOnClickListener {
        fun onItemClick(accessory: Accessory)
    }

}