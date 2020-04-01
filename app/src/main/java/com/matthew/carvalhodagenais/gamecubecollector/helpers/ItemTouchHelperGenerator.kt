package com.matthew.carvalhodagenais.gamecubecollector.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.matthew.carvalhodagenais.gamecubecollector.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ItemTouchHelperGenerator(context: Context) {

    fun generate(): ItemTouchHelper.SimpleCallback {
        return recyclerViewTouchHelper
    }

    /**
     * ItemTouchHelper used to swipe RecyclerView items
     * and create background/child views under each item
     */
    private var recyclerViewTouchHelper = object: ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addSwipeRightBackgroundColor(context.getColor(R.color.red))
                .setSwipeRightLabelColor(Color.WHITE)
                .addSwipeRightLabel(context.getString(R.string.ith_delete))
                .addSwipeRightActionIcon(R.drawable.ic_delete_white_32dp)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}