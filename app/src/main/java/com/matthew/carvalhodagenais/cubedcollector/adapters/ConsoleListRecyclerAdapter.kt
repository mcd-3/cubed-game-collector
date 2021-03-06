package com.matthew.carvalhodagenais.cubedcollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Console
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import kotlinx.android.synthetic.main.console_item.view.*
import java.util.*

class ConsoleListRecyclerAdapter: ListAdapter<Console, ConsoleListRecyclerAdapter.ConsoleHolder>(
    DIFF_CALLBACK), Filterable {

    private var listener: ItemOnClickListener? = null
    private lateinit var searchableList: List<Console>

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Console>() {
            override fun areContentsTheSame(oldItem: Console, newItem: Console): Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.description == oldItem.description &&
                        oldItem.regionId == newItem.regionId &&
                        oldItem.isModded == newItem.isModded &&
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

        // Place appropriate console image
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

    fun getConsole(position: Int): Console {
        return getItem(position)
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    fun setSearchableList(list: List<Console>) {
        searchableList = list
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

    private var searchFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Console>()
            if (constraint!!.isEmpty()) {
                filteredList = searchableList.toMutableList()
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                searchableList.forEach {
                    if (it.title.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            submitList(filteredList)
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }
}