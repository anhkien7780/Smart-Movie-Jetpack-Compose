package com.kiennv31.smartmovie.data.remote.api

object ApiConstants {
    const val API_KEY = "d5b97a6fad46348136d87b78895a0c06"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    // Paths
    const val MOVIE_ID = "movieId"
    const val CONFIGURATION = "configuration"
    const val POPULAR = "movie/popular"
    const val TOP_RATED = "movie/top_rated"
    const val UPCOMING = "movie/upcoming"
    const val NOW_PLAYING = "movie/now_playing"
    const val MOVIE_DETAIL = "movie/{$MOVIE_ID}"
    const val SIMILAR_MOVIES = "movie/{$MOVIE_ID}/similar"
    const val SEARCH_MOVIE = "search/movie"
    const val GENRE_MOVIE_LIST = "genre/movie/list"
    const val DISCOVER_MOVIE = "discover/movie"

    // Queries
    const val API_KEY_QUERY = "api_key"
    const val PAGE_QUERY = "page"
    const val QUERY_QUERY = "query"
    const val WITH_GENRES_QUERY = "with_genres"
}
