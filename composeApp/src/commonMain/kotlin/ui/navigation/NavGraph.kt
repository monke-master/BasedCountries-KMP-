package ui.navigation

import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ui.country.CountryScreen
import ui.list.CountriesListScreen

fun RootComposeBuilder.navigationGraph() {
    screen("country_list") {
        CountriesListScreen()
    }
    screen("country") { params ->
        CountryScreen(params as String)
    }
}