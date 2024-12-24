package com.pablok.kinopoisklight.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun MyTextField(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )

        ,
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth()
            )
        },
/*        supportingText = {
            Text(
                text = "Supporting text",
                modifier = Modifier.fillMaxWidth()
            )
        },*/
        trailingIcon = {
            Row(Modifier.padding(end = 8.dp)

            ) {
                if (text.isNotEmpty()) {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier.clickable {
                            onTextChanged("")
                        }
                    )
                    Spacer(Modifier.width(24.dp))
                }
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    modifier = Modifier
                        .scale(1.5f)
                        .clickable {
                            keyboardController?.hide()
                            onSearchClicked(text)
                    }
                )
            }

        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSearchClicked(text)
            }
        )
    )
}

@Preview
@Composable
fun MyTextFieldPreview() {
    KinopoiskLightTheme {
        Box(Modifier
            .padding(16.dp)
        ) {


            var text by remember { mutableStateOf("") }
            MyTextField(
                title = "Title",
                text = text,
                onTextChanged = {
                    text = it
                },
                onSearchClicked = {

                }
            )
        }
    }
}