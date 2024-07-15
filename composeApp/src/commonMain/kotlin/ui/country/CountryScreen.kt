package ui.country

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.odyssey.StoredViewModel
import domain.Country
import domain.FullCountry
import domain.Russia
import ui.LoadingPlaceholder
import ui.ShimmerImage

@Composable
fun CountryScreen(
    countryName: String
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        StoredViewModel(factory = { CountryViewModel() }) { viewModel ->
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
    country: FullCountry
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = country.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.height(240.dp).padding(top = 16.dp)
        ) {
            ShimmerImage(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                source = country.flag
            )
            ShimmerImage(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                source = country.coatOfArms
            )
        }

    }

}

@Composable
fun CountryScreenPreview() {
    MaterialTheme {
        Surface {
            CountryData(country = Russia)
        }
    }
}

