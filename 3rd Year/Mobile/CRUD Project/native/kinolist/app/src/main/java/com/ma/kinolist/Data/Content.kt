package com.ma.kinolist.Data

import java.util.*

object Content {

    var items: MutableList<Movie> = ArrayList()
    var item_map: MutableMap<Int, Movie> = HashMap()
    val genres = arrayOf("Action", "Animation", "Comedy", "Crime", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller", "Western")
    val status_map = mapOf(0 to "To watch", 1 to "Watching", 2 to "Completed")
    val status_values = arrayOf("To watch", "Watching", "Completed")

    init {
        val movie1 = Movie(Movie.movieId++, "Stalker", 1979, arrayListOf("Drama", "Fantasy"), 2, "00:00", 1, 1)
        val movie2 = Movie(Movie.movieId++, "The Undoing", 2020, arrayListOf("Drama", "Thriller"), 1, "00:00", 3, 1)
        val movie3 = Movie(Movie.movieId++, "Oldboy", 2003, arrayListOf("Action", "Drama", "Mystery"), 2, "00:00", 1, 1)
        val movie4 = Movie(Movie.movieId++, "Stalker", 1976, arrayListOf("Crime", "Drama"), 2, "00:00", 1, 1)
        val movie5 = Movie(Movie.movieId++, "Lorem", 2021, arrayListOf("Mystery"), 0, "00:00", 1, 1)
        addItem(movie1)
        addItem(movie2)
        addItem(movie3)
        addItem(movie4)
        addItem(movie5)
    }

    fun addItem(item: Movie) {
        items.add(item)
        item_map[item.id] = item
    }

    data class Movie(val id: Int, var title: String, var year: Int = 0, var genres: ArrayList<String> = ArrayList(), var status: Int = 0,
                     var stopped: String = "00:00", var episode: Int = 1, var season: Int = 1) {
        override fun toString(): String = "$id - $title - $year"
        fun titleString(): String = "$title - $year"
        fun genresList(): String = genres.joinToString (separator = ", "){ it}
        fun progressString(): String = "$stopped/$episode/$season"

        companion object{
            var movieId = 1
        }
    }
}