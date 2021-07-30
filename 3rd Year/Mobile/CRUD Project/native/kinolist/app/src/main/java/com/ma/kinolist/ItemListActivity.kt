package com.ma.kinolist

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ma.kinolist.Data.Content
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*


class ItemListActivity : AppCompatActivity() {
//    var adapter: SimpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter(Content.items)
    var adapter: SimpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        // bind add button
        addBtn.setOnClickListener { view ->
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }

        // setup the recyclerview adapter
        item_list.adapter = adapter

        // add the swipe to delete action
        swipeToDelete()
    }

    // swipe to delete
    private fun swipeToDelete(){
        val swipeAction = object: SwipeToDelete(this, 0, ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)

                val builder = AlertDialog.Builder(ContextThemeWrapper(this@ItemListActivity, R.style.AlertDialogCustom))

                builder.setMessage("Are you sure you want to delete ${adapter.getItem(viewHolder.adapterPosition)}")
                        .setTitle("Delete")
                        .setPositiveButton("Delete") { dialog, id ->
                            adapter.removeItem(viewHolder.adapterPosition)
                        }
                        .setNegativeButton("Cancel") { dialog, id ->
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                            dialog.dismiss()
                        }
                builder.create()
                builder.show()


            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView: View = viewHolder.itemView
                val deleteIcon = ContextCompat.getDrawable(this@ItemListActivity, android.R.drawable.ic_menu_delete)

                if (deleteIcon != null) {
                    val iconMargin: Int = (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconTop: Int = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconBottom: Int = iconTop + deleteIcon.intrinsicHeight

                    if (dX < 0) {
                        val iconLeft: Int = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                        val iconRight: Int = itemView.right - iconMargin
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    }
                    deleteIcon.draw(c)
                }
            }
        }
        val removeAction = ItemTouchHelper(swipeAction)
        removeAction.attachToRecyclerView(item_list)
    }


    fun addItem(item: Content.Movie){
        adapter.addItem(item)
    }

    fun updateItem(item: Content.Movie){
        adapter.updateItem(item)
    }

    // adapter
    class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private var values: MutableList<Content.Movie> = Content.items
        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Content.Movie
                println(item)
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.titleView.text = item.titleString()
            holder.genresView.text = item.genresList()
            holder.progressView.text = item.progressString()
            println(item.status)
            when(item.status){

                0 -> holder.titleView.setTextColor(Color.parseColor("#026e71"))
                1 -> holder.titleView.setTextColor(Color.parseColor("#a30b2a"))
                else -> holder.titleView.setTextColor(Color.parseColor("#f9a425"))
            }


            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        fun removeItem(position: Int){
            values.removeAt(position)
            notifyDataSetChanged()
        }

        fun addItem(item: Content.Movie) {
            values.add(item)
            Content.item_map[item.id] = item
            notifyDataSetChanged()
        }

        fun getItem(position: Int): Content.Movie{
            return values[position]
        }

        fun updateItem(item: Content.Movie){
            val current = Content.item_map[item.id]
            values[values.indexOf(current)] = item
            Content.item_map[item.id] = item
            notifyDataSetChanged()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.title_item_view
            val genresView: TextView = view.genres_item_view
            val progressView: TextView = view.progress_item_view

        }

    }

}
