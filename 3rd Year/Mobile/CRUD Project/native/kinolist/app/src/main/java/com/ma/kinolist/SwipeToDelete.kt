package com.ma.kinolist

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


abstract class SwipeToDelete (context: Context, dragDir: Int, swipeDir: Int): ItemTouchHelper.SimpleCallback(dragDir, swipeDir){

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

//    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//
//        val itemView: View = viewHolder.itemView
//        val deleteIcon = ContextCompat.getDrawable(requireActivity(), android.R.drawable.ic_delete);
//
//        val iconMargin: Int = (itemView.getHeight() - 10) / 2
//        val iconTop: Int = itemView.getTop() + (itemView.getHeight() - 10) / 2
//        val iconBottom: Int = iconTop + 10
//
//        if (dX > 0) {
//            val iconLeft: Int = itemView.getLeft() + iconMargin + 10
//            val iconRight: Int = itemView.getLeft() + iconMargin
//            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + dX.toInt(), itemView.getBottom())
//        } else if (dX < 0) {
//            val iconLeft: Int = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth()
//            val iconRight: Int = itemView.getRight() - iconMargin
//            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            background.setBounds(itemView.getRight() + dX.toInt(), itemView.getTop(), itemView.getRight(), itemView.getBottom())
//        } else {
//            background.setBounds(0, 0, 0, 0)
//        }
//
//        background.draw(c)
//        deleteIcon.draw(c)
//    }
}