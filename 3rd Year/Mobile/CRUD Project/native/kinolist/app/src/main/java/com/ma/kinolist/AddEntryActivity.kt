package com.ma.kinolist

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import com.ma.kinolist.Data.Content
import kotlinx.android.synthetic.main.activity_add_entry.*
import kotlinx.android.synthetic.main.activity_item_list.toolbar
import kotlin.collections.ArrayList


class AddEntryActivity : AppCompatActivity() {

    private val selectedGenres = ArrayList<String>()
    private var selectedStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        add_status.text = selectedStatus.toString()

        finishBtn.setOnClickListener {

            val title = add_title.text.toString()
            if (title.isEmpty()){
                Snackbar.make(it, "Please provide a title", Snackbar.LENGTH_LONG).show()
            }
            else {
                val current = Content.Movie.movieId++
                try {
                    val year = add_year.text.toString().toInt()
                    val genres = selectedGenres
                    val status = selectedStatus
                    val stopped = add_stop_time.text.toString()
                    val episode = current_episode.text.toString().toInt()
                    val season = current_season.text.toString().toInt()
                    val movie = Content.Movie(current, title, year, genres, status, stopped, episode, season)
                    ItemListActivity().addItem(movie)
                } catch (e: java.lang.Exception) {
                    val movie = Content.Movie(current, title, 0, selectedGenres, selectedStatus)
                    ItemListActivity().addItem(movie)
                }
                NavUtils.navigateUpTo(this, Intent(this, ItemListActivity::class.java))
            }

        }

        add_genres.setOnClickListener{

            val items = Content.genres
            val selectedList = ArrayList<Int>()
            val builder = AlertDialog.Builder(ContextThemeWrapper(this@AddEntryActivity, R.style.AlertDialogCustom))

            builder.setTitle("Select genres")
            builder.setMultiChoiceItems(items, null
            ) { dialog, which, isChecked ->
                if (isChecked) {
                    selectedList.add(which)
                } else if (selectedList.contains(which)) {
                    selectedList.remove(Integer.valueOf(which))
                }
            }

            builder.setPositiveButton("Done") { dialogInterface, i ->
                for (j in selectedList.indices) {
                    selectedGenres.add(items[selectedList[j]])
                }
                add_genres.text = selectedGenres.joinToString (separator = ", "){ it}
            }
            builder.show()
        }

        add_status.setOnClickListener{

            val items = Content.status_values
            var selectedItem = 0
            val builder = AlertDialog.Builder(ContextThemeWrapper(this@AddEntryActivity, R.style.AlertDialogCustom))

            builder.setTitle("Select genres")
            builder.setSingleChoiceItems(items, 0
            ) { dialog, selected ->
                selectedItem = selected
            }

            builder.setPositiveButton("Done") { dialogInterface, i ->
                selectedStatus = selectedItem
                add_status.text = items[selectedStatus]
            }
            builder.show()
        }

        episode_incr.setOnClickListener {
            current_episode.text = (current_episode.text.toString().toInt() + 1).toString()
        }

        episode_decr.setOnClickListener {
            val current = current_episode.text.toString().toInt()
            if (current > 1) current_episode.text = (current - 1).toString()
        }

        season_incr.setOnClickListener {
            current_season.text = (current_season.text.toString().toInt() + 1).toString()
        }

        season_decr.setOnClickListener {
            val current = current_season.text.toString().toInt()
            if (current > 1) current_season.text = (current - 1).toString()
        }

    }
    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpTo(this, Intent(this, ItemListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}