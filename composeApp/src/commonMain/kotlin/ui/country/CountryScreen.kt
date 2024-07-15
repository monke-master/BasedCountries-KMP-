package ui.country

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adeo.kviewmodel.compose.ViewModel
import domain.Country
import ui.LoadingPlaceholder

@Composable
fun CountryScreen(
    countryName: String
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ViewModel(factory = { CountryViewModel() }) { viewModel ->
            LaunchedEffect(Unit) {
                viewModel.obtainEvent(CountryEvent.LoadData(countryName))
            }

            val viewState = viewModel.viewStates().collectAsState()

            when (val state = viewState.value) {
                is CountryState.Error -> ErrorState(state.error)
                CountryState.Idle -> IdleState()
                CountryState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is CountryState.Success -> CountryData(state.country)
            }
        }
    }
}

@Composable
private fun IdleState() {}

@Composable
private fun ErrorState(
    error: Throwable
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error.message ?: "404")
    }
}

@Composable
private fun CountryData(
    country: Country
) {
    Text(country.name)
}