package com.example.portfolioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.portfolioapp.ui.theme.PortfolioAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PortfolioScreen()
                }
            }
        }
    }
}

@Composable
fun PortfolioScreen() {

    var name by rememberSaveable { mutableStateOf("Oma Nimi") }
    var aboutText by rememberSaveable { mutableStateOf("Kuvaus") }
    var editMode by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profiilikuva",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (editMode) {
            EditableProfileSection(
                name = name,
                about = aboutText,
                onNameChange = { name = it },
                onAboutChange = { aboutText = it },
                onSave = { editMode = false }
            )
        } else {
            ProfileInfoSection(
                name = name,
                about = aboutText,
                onEditClick = { editMode = true }
            )
        }
    }
}

@Composable
fun ProfileInfoSection(name: String, about: String, onEditClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(
            text = about,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray
        )
        Button(onClick = onEditClick) {
            Text("Muokkaa profiilia")
        }
    }
}

@Composable
fun EditableProfileSection(
    name: String,
    about: String,
    onNameChange: (String) -> Unit,
    onAboutChange: (String) -> Unit,
    onSave: () -> Unit
) {
    var newName by remember { mutableStateOf(TextFieldValue(name)) }
    var newAbout by remember { mutableStateOf(TextFieldValue(about)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text("Nimi") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = newAbout,
            onValueChange = { newAbout = it },
            label = { Text("Esittely") },
            modifier = Modifier.height(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onNameChange(newName.text)
            onAboutChange(newAbout.text)
            onSave()
        }) {
            Text("Tallenna muutokset")
        }
    }
}
