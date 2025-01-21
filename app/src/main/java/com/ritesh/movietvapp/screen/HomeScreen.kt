package com.ritesh.movietvapp.screen

import HomeViewModel
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesh.movietvapp.component.ShimmerListItem
import com.ritesh.movietvapp.component.ToggleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navigateToDetails: (Int) -> Unit) {
    val moviesSelected = rememberSaveable { mutableStateOf(true) }
    val movieTitles by homeViewModel.movieTitles.observeAsState(emptyList())
    val tvShowTitles by homeViewModel.tvShowTitles.observeAsState(emptyList())
    val errorMessage by homeViewModel.errorMessage.observeAsState()
    val isLoading by homeViewModel.isLoading.observeAsState(true)

    val snackbarHostState = remember { SnackbarHostState() }

    val titles = if(moviesSelected.value) movieTitles else tvShowTitles

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MovieTVShow",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                ToggleButton(
                    text = "Movies",
                    isSelected = moviesSelected.value,
                    onClick = {
                        moviesSelected.value = true
                        homeViewModel.updateContentType("movie")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                ToggleButton(
                    text = "TV Shows",
                    isSelected = !moviesSelected.value,
                    onClick = {
                        moviesSelected.value = false
                        homeViewModel.updateContentType("tv_series")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }

            LazyColumn {
                if (isLoading) {
                    items(10) {
                        ShimmerListItem()
                    }
                } else {
                    items(titles) { title ->
                        ListItem(
                            headlineContent = { Text(text = title.title, fontSize = 20.sp) },
                            supportingContent = { Text(text = title.year.toString()) },
                            colors = ListItemDefaults.colors(
                                containerColor = Color.DarkGray,
                                headlineColor = Color.White,
                                supportingColor = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { navigateToDetails(title.id) }
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(8.dp, shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }
    }
}



