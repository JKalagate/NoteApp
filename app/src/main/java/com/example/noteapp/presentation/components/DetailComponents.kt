package com.example.noteapp.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.ui.theme.BgColor
import com.example.noteapp.ui.theme.BgDarkBlue
import com.example.noteapp.ui.theme.FrameColor
import com.example.noteapp.ui.theme.Primary
import com.example.noteapp.ui.theme.componentShapes
import com.example.noteapp.utils.Utils


@Composable
fun TitleTextComponent(
    changedText: String?,
    hintText: String,
    containerColor: Int,
    onTitleTextChanged: (String) -> Unit
) {
    var titleText by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(max = 58.dp)
            .clip(componentShapes.small)
            .background(Utils.gradients[containerColor]),
        value = changedText ?: titleText,
        onValueChange = {
            titleText = it
            onTitleTextChanged(it)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = FrameColor,
            cursorColor = Primary,
            focusedLabelColor = Primary,
            focusedContainerColor = FrameColor,
            unfocusedContainerColor = FrameColor,
            errorContainerColor = BgColor
        ),
        placeholder = {
            Text(
                text = hintText,
                color = Color.Gray
            )
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}


@Composable
fun NoteTextComponent(
    changedText: String?,
    hintText: String,
    modifier: Modifier,
    containerColor: Int,
    onNoteTextChanged: (String) -> Unit
) {

    var noteText by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(componentShapes.small)
            .background(Utils.gradients[containerColor]),
        value = changedText ?: noteText,
        onValueChange = {
            noteText = it
            onNoteTextChanged(it)

        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = FrameColor,
            cursorColor = Primary,
            focusedLabelColor = Primary,
            focusedContainerColor = FrameColor,
            unfocusedContainerColor = FrameColor,
            errorContainerColor = BgColor
        ),
        placeholder = {
            Text(
                text = hintText,
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}

@Composable
fun ColorsRowsComponent(
    gradients: List<Brush>,
    modifier: Modifier,
    onColorChange: (Int) -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp),
        Alignment.Center
    ) {
        LazyHorizontalGrid(
            modifier = modifier,
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                itemsIndexed(gradients) { colorIndex, color ->
                    ColorItemComponent(color = color) {
                        onColorChange.invoke(colorIndex)
                    }
                }
            }
        )
    }

}

@Composable
fun ColorItemComponent(
    color: Brush,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(35.dp)
            .width(35.dp)
            .clickable {
                onClick.invoke()
            }
            .background(color)
            .border(1.dp, Color.Gray, shape = RectangleShape)
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItemComponent(
    titleValue: String,
    noteValue: String,
    onClick: () -> Unit,
    containerColor: Int,
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        shape = RoundedCornerShape(size = 12.dp)
    ) {

        Box(
            modifier = Modifier
                .background(Utils.gradients[containerColor])
                .fillMaxWidth()
        ) {

            Column {
                Text(
                    text = titleValue,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    ),
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
                )

                Text(
                    text = noteValue,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        lineHeight = 30.sp
                    ),
                    maxLines = 8,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    ),
                )
            }

        }
    }
}


@Composable
fun FloatingActionButtonComponent(
    value: String,
    onNavigate: () -> Unit
) {
    FloatingActionButton(
        containerColor = BgDarkBlue,
        onClick = { onNavigate.invoke() },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.width(130.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, start = 15.dp, end = 15.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = value,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun MainTopAppBarComponent(
    value: String,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Primary)
            .padding(top = 3.dp, bottom = 3.dp, start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SpecialTextComponent(value, 18.sp)

        Spacer(modifier = Modifier.weight(1f))

        AppBarIconComponent(
            onClick = { onLogout.invoke() },
            icon = Icons.Filled.ExitToApp
        )
    }
}

@Composable
fun NoteTopAppBarComponent(
    title: String,
    value: String,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onTextButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Primary)
            .padding(top = 3.dp, bottom = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppBarIconComponent(
            onClick = { onBack.invoke() },
            icon = Icons.Filled.ArrowBack
        )

        SpecialTextComponent(title, 18.sp)

        Spacer(modifier = Modifier.weight(1f))

        AppBarIconComponent(
            onClick = { onDelete.invoke() },
            icon = Icons.Outlined.Delete
        )

        TextButton(
            onClick = { onTextButton.invoke() }) {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color.White
                )
            )
        }
    }
}


@Composable
fun AppBarIconComponent(
    onClick: () -> Unit,
    icon: ImageVector
) {
    IconButton(onClick = {
        onClick.invoke()
    }) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}


@Composable
fun SpecialTextComponent(
    title: String,
    fontSize: TextUnit
) {

    val shadowOffset = Offset(1f, 2f)

    Text(
        text = title,
        color = Color.White,
        style = TextStyle(
            color = Primary,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = fontSize,
            shadow = Shadow(
                color = Color.White,
                offset = shadowOffset, 1f
            )
        )

    )
}