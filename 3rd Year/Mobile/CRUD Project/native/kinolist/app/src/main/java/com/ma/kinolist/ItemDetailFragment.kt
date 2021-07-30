package com.ma.kinolist


import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ma.kinolist.Data.Content
import kotlinx.android.synthetic.main.activity_add_entry.add_title
import kotlinx.android.synthetic.main.activity_add_entry.add_year
import kotlinx.android.synthetic.main.activity_item_detail.toolbar
import kotlinx.android.synthetic.main.fragment_item_detail.*
import kotlinx.android.synthetic.main.fragment_item_detail.add_genres
import kotlinx.android.synthetic.main.fragment_item_detail.add_status
import kotlinx.android.synthetic.main.fragment_item_detail.add_stop_time
import kotlinx.android.synthetic.main.fragment_item_detail.current_episode
import kotlinx.android.synthetic.main.fragment_item_detail.current_season
import kotlinx.android.synthetic.main.fragment_item_detail.view.*

class ItemDetailFragment : Fragment()  {

    private var item: Content.Movie? = null
    private var selectedGenres = ArrayList<String>()
    private var selectedStatus = 0
    private val genres = Content.genres
    private var preselectedGenres = ArrayList<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // get data passed from activity and set some things
                item = Content.item_map[it.getInt(ARG_ITEM_ID)]
                activity?.toolbar?.title = item?.title
                selectedGenres = item?.genres?: ArrayList()
                selectedStatus = item?.status?:0
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_detail, container, false)

        // Show content
        item?.let {
            rootView.add_title.text = SpannableStringBuilder(it.title)
            rootView.add_year.text = SpannableStringBuilder(it.year.toString())
            rootView.add_genres.text = it.genresList()
            rootView.add_status.text = Content.status_map[it.status]
            rootView.add_stop_time.text = SpannableStringBuilder(it.stopped)
            rootView.current_episode.text = it.episode.toString()
            rootView.current_season.text = it.season.toString()

        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize genre checklist
        selected()

        // buttons
        updateBtn.setOnClickListener {
            val title = add_title.text.toString()

            if (title.isEmpty()){
                Snackbar.make(it, "Please provide a title", Snackbar.LENGTH_LONG).show()
            }
            else {
                try {
                    val year = add_year.text.toString().toInt()
                    val stopped = add_stop_time.text.toString()
                    val episode = current_episode.text.toString().toInt()
                    val season = current_season.text.toString().toInt()
                    val movie = Content.Movie(item!!.id, title, year, selectedGenres, selectedStatus, stopped, episode, season)
                    ItemListActivity().updateItem(movie)
                    val snack = Snackbar.make(it, "Update complete", Snackbar.LENGTH_LONG)
                    snack.show()
                    snack.setBackgroundTint(Color.parseColor("#108c02"))

                } catch (e: java.lang.Exception) {
                    val movie = Content.Movie(item!!.id, title, 0, selectedGenres)
                    ItemListActivity().updateItem(movie)
                }
            }
        }



        add_genres.setOnClickListener{
            val selectedList = ArrayList<Int>()
            val builder = AlertDialog.Builder(ContextThemeWrapper(this.activity, R.style.AlertDialogCustom))

            builder.setTitle("Select genres")
            builder.setMultiChoiceItems(genres, preselectedGenres.toBooleanArray()
            ) { dialog, which, isChecked ->
                println(which)
                println(isChecked)
                if (isChecked) {
                    selectedList.add(which)
                    preselectedGenres[which] = true
                } else if (selectedList.contains(which)) {
                    selectedList.remove(Integer.valueOf(which))
                    preselectedGenres[which] = false
                }
                else{
                    preselectedGenres[which] = false
                }
            }

            builder.setPositiveButton("Done") { dialogInterface, i ->
                val newSelectedGenres = ArrayList<String>()
                for (j in preselectedGenres.indices) {
                    if (preselectedGenres[j]) {
                        newSelectedGenres.add(genres[j])
                    }
                }
                println("${selectedGenres.size} - ${newSelectedGenres.size}")
                println("${selectedGenres} - ${newSelectedGenres}")
                println("${preselectedGenres} -\n ${genres.size} - ${preselectedGenres.size}")
                selectedGenres = newSelectedGenres
                add_genres.text = selectedGenres.joinToString (separator = ", "){ it}
            }
            builder.show()
        }

        add_status.setOnClickListener{

            val items = Content.status_values
            val builder = AlertDialog.Builder(ContextThemeWrapper(this.activity, R.style.AlertDialogCustom))

            builder.setTitle("Select genres")
            builder.setSingleChoiceItems(items, selectedStatus
            ) { dialog, selected ->
                selectedStatus = selected
            }

            builder.setPositiveButton("Done") { dialogInterface, i ->
                add_status.text = items[selectedStatus]
            }
            builder.show()
        }

        episode_incr_update.setOnClickListener {
            current_episode.text = (current_episode.text.toString().toInt() + 1).toString()
        }

        episode_decr_update.setOnClickListener {
            val current = current_episode.text.toString().toInt()
            if (current > 1) current_episode.text = (current - 1).toString()
        }

        season_incr_update.setOnClickListener {
            current_season.text = (current_season.text.toString().toInt() + 1).toString()
        }

        season_decr_update.setOnClickListener {
            val current = current_season.text.toString().toInt()
            if (current > 1) current_season.text = (current - 1).toString()
        }
    }



    private fun selected(){

        for (i in genres.indices){
            println(genres[i])
            if (selectedGenres.contains(genres[i])){
                preselectedGenres.add(true)
            }
            else {
                preselectedGenres.add(false)
            }
        }
    }



    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}


//fun help(){
//    println("please")
//}