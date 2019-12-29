package com.matthew.carvalhodagenais.gamecubecollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.android.synthetic.main.game_item.view.*

class GameListRecyclerAdapter: ListAdapter<Game, GameListRecyclerAdapter.GameHolder>(DIFF_CALLBACK) {

    private var listener: ItemOnClickListener ?= null

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Game>() {

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.developers == newItem.developers &&
                        oldItem.publishers == newItem.publishers &&
                        oldItem.releaseDate == newItem.releaseDate &&
                        oldItem.regionId == newItem.regionId &&
                        oldItem.boughtDate == newItem.boughtDate &&
                        oldItem.condition == newItem.condition &&
                        oldItem.pricePaid == newItem.pricePaid &&
                        oldItem.hasCase == newItem.hasCase &&
                        oldItem.hasManual == newItem.hasManual)
            }

            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val game = getItem(position)
        holder.titleTextView.text = game.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_item, parent, false)
        return GameHolder(itemView)
    }

    inner class GameHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var titleTextView = view.title_text_view
    }

    interface ItemOnClickListener {
        fun onItemClick(game: Game) //use this in activity
    }

    fun setItemOnClickListener(listener: ItemOnClickListener) {
        this.listener = listener
    }


}