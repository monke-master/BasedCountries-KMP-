package ru.monke.countires

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ui.navigation.navigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val odysseyConfiguration = OdysseyConfiguration(
                canvas = this,
                startScreen = StartScreen.Custom("country_list")
            )
            
            setNavigationContent(configuration = odysseyConfiguration) {
                navigationGraph()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}