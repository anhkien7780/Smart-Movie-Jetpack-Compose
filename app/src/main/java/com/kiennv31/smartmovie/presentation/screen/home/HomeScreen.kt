package com.kiennv31.smartmovie.presentation.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.presentation.common.BaseAppBar
import com.kiennv31.smartmovie.presentation.screen.home.page.movies.MoviesPage
import com.kiennv31.smartmovie.presentation.screen.home.page.movies.MoviesViewModel
import com.kiennv31.smartmovie.presentation.screen.home.page.nowplaying.NowPlayingPage
import com.kiennv31.smartmovie.presentation.screen.home.page.nowplaying.NowPlayingViewModel
import com.kiennv31.smartmovie.presentation.screen.home.page.popular.PopularPage
import com.kiennv31.smartmovie.presentation.screen.home.page.popular.PopularViewModel
import com.kiennv31.smartmovie.presentation.screen.home.page.toprated.TopRatedPage
import com.kiennv31.smartmovie.presentation.screen.home.page.toprated.TopRatedViewModel
import com.kiennv31.smartmovie.presentation.screen.home.page.upcoming.UpcomingPage
import com.kiennv31.smartmovie.presentation.screen.home.page.upcoming.UpcomingViewModel
import kotlinx.coroutines.launch

enum class HomePage(@param:StringRes val titleRes: Int) {
    Movies(R.string.movies),
    Popular(R.string.popular),
    TopRated(R.string.top_rated),
    Upcoming(R.string.upcoming),
    NowPlaying(R.string.now_playing)
}

@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pages = HomePage.entries
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(id = R.string.smart_movie),
                actionIcon = {
                    Icon(
                        painter = painterResource(
                            if (state.isGridView) R.drawable.ic_grid_view else R.drawable.ic_dehaze
                        ),
                        contentDescription = null,
                        tint = Color.Blue
                    )
                },
                onActionClick = { viewModel.toggleViewMode() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = dimensionResource(id = R.dimen.padding_big),
                divider = {}
            ) {
                pages.forEachIndexed { index, page ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = stringResource(id = page.titleRes)) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                when (pages[pageIndex]) {
                    HomePage.Movies -> {
                        val moviesViewModel: MoviesViewModel = hiltViewModel()
                        val moviesState by moviesViewModel.state.collectAsState()
                        LaunchedEffect(moviesState.isLoading) {
                            viewModel.updateSubPageLoading(pageIndex, moviesState.isLoading)
                        }
                        MoviesPage(
                            viewModel = moviesViewModel,
                            isGridView = state.isGridView,
                            onSeeAllClick = { index ->
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            onMovieClick = onMovieClick
                        )
                    }

                    HomePage.Popular -> {
                        val popularViewModel: PopularViewModel = hiltViewModel()
                        val popularState by popularViewModel.state.collectAsState()
                        LaunchedEffect(popularState.isLoading, popularState.isLoadMore) {
                            viewModel.updateSubPageLoading(
                                pageIndex,
                                popularState.isLoading || popularState.isLoadMore
                            )
                        }
                        PopularPage(
                            viewModel = popularViewModel,
                            isGridView = state.isGridView,
                            onMovieClick = onMovieClick
                        )
                    }

                    HomePage.TopRated -> {
                        val topRatedViewModel: TopRatedViewModel = hiltViewModel()
                        val topRatedState by topRatedViewModel.state.collectAsState()
                        LaunchedEffect(topRatedState.isLoading, topRatedState.isLoadMore) {
                            viewModel.updateSubPageLoading(
                                pageIndex,
                                topRatedState.isLoading || topRatedState.isLoadMore
                            )
                        }
                        TopRatedPage(
                            viewModel = topRatedViewModel,
                            isGridView = state.isGridView,
                            onMovieClick = onMovieClick
                        )
                    }

                    HomePage.Upcoming -> {
                        val upcomingViewModel: UpcomingViewModel = hiltViewModel()
                        val upcomingState by upcomingViewModel.state.collectAsState()
                        LaunchedEffect(upcomingState.isLoading, upcomingState.isLoadMore) {
                            viewModel.updateSubPageLoading(
                                pageIndex,
                                upcomingState.isLoading || upcomingState.isLoadMore
                            )
                        }
                        UpcomingPage(
                            viewModel = upcomingViewModel,
                            isGridView = state.isGridView,
                            onMovieClick = onMovieClick
                        )
                    }

                    HomePage.NowPlaying -> {
                        val nowPlayingViewModel: NowPlayingViewModel = hiltViewModel()
                        val nowPlayingState by nowPlayingViewModel.state.collectAsState()
                        LaunchedEffect(nowPlayingState.isLoading, nowPlayingState.isLoadMore) {
                            viewModel.updateSubPageLoading(
                                pageIndex,
                                nowPlayingState.isLoading || nowPlayingState.isLoadMore
                            )
                        }
                        NowPlayingPage(
                            viewModel = nowPlayingViewModel,
                            isGridView = state.isGridView,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}
