package com.example.noteapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.domain.ui.DetailUIEvent
import com.example.noteapp.navigation.Screens
import com.example.noteapp.presentation.components.ColorsRowsComponent
import com.example.noteapp.presentation.components.NoteTextComponent
import com.example.noteapp.presentation.components.NoteTopAppBarComponent
import com.example.noteapp.presentation.components.TitleTextComponent
import com.example.noteapp.presentation.detail.DetailViewModel
import com.example.noteapp.ui.theme.BgLightBlue
import com.example.noteapp.utils.Utils


@Composable
fun DetailScreen(
    navController: NavHostController,
    noteId: String?,
    detailViewModel: DetailViewModel = hiltViewModel(),

) {


    LaunchedEffect(key1 = Unit) {
        if (noteId != null) {
            detailViewModel.getNote(noteId)
        }
    }

    Scaffold(
        topBar = {
            NoteTopAppBarComponent(
                title = if (noteId != null) stringResource(id = R.string.update_note)
                else stringResource(id = R.string.add_note),
                value = stringResource(id = R.string.save),
                onBack = {
                    navController.popBackStack()
                },
                onDelete = {
                    if (noteId != null) {
                        detailViewModel.deleteNote(noteId) {
                            navController.navigate(Screens.HomeScreen.route)
                        }
                    }
                },
                onTextButton = {
                    if (noteId != null) {
                        detailViewModel.updateNote(noteId) {
                            navController.navigate(Screens.HomeScreen.route)
                        }
                    } else {
                        detailViewModel.addNote {
                            navController.navigate(Screens.HomeScreen.route)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgLightBlue)
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            ColorsRowsComponent(
                gradients = Utils.gradients,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
                onColorChange = {
                    detailViewModel.onEvent(DetailUIEvent.BrushChange(it))
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            TitleTextComponent(
                changedText = if (noteId != null) detailViewModel.noteUiState.title else null,
                hintText = stringResource(id = R.string.title),
                containerColor = detailViewModel.noteUiState.brushIndex,
                onTitleTextChanged = {
                    detailViewModel.onEvent(DetailUIEvent.TitleChange(it))
                }
            )

            NoteTextComponent(
                changedText = if (noteId != null) detailViewModel.noteUiState.description else null,
                hintText = stringResource(id = R.string.note),
                modifier = Modifier.weight(5f),
                containerColor = detailViewModel.noteUiState.brushIndex,
                onNoteTextChanged = {
                    detailViewModel.onEvent(DetailUIEvent.DescriptionChange(it))
                }
            )
        }
    }
}