package com.example.noteapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.navigation.Screens
import com.example.noteapp.presentation.components.FloatingActionButtonComponent
import com.example.noteapp.presentation.components.MainTopAppBarComponent
import com.example.noteapp.presentation.components.NoteItemComponent
import com.example.noteapp.presentation.detail.DetailViewModel
import com.example.noteapp.ui.theme.BgLightBlue

@Composable
fun HomeScreen(
    navController: NavHostController,
    firstLog: String?,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {

    val noteList by detailViewModel.noteList.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        if (firstLog == null) {
            detailViewModel.loadNotes()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButtonComponent(
                    value = stringResource(id = R.string.new_note)
                ) {
                    navController.navigate(Screens.DetailScreen.route)
                }
            },
            topBar = {
                MainTopAppBarComponent(
                    value = stringResource(id = R.string.your_notes),
                    onLogout = {
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpTo(Screens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BgLightBlue)
                    .padding(top = 20.dp)
            ) {

                if (noteList.isNotEmpty()) {

                    LazyVerticalStaggeredGrid(
                        modifier = Modifier
                            .padding(padding),
                        columns = StaggeredGridCells.Fixed(2),

                        ) { items(noteList) { note ->
                        NoteItemComponent(
                            titleValue = note.title,
                            noteValue = note.description,
                            onClick = {
                                navController.navigate("${Screens.DetailScreen.route}?id=${note.documentId}")
                            },
                            containerColor = note.brushIndex
                        )
                    }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Notes Found")
                    }
                }

            }
        }
        if (detailViewModel.inProgress) {
            CircularProgressIndicator()
        }
    }
}


