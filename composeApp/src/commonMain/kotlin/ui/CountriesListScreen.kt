package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.adeo.kviewmodel.compose.ViewModel
import domain.Country
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CountriesListScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ViewModel(factory = { CountriesListViewModel() }) { viewModel ->
            LaunchedEffect(Unit) {
                viewModel.obtainEvent(CountriesListEvent.LoadData)
            }

            val viewState = viewModel.viewStates().collectAsState()

            when (val state = viewState.value) {
                is CountriesListState.Error -> ErrorState(state.error)
                CountriesListState.Idle -> IdleState()
                CountriesListState.Loading -> LoadingState()
                is CountriesListState.Success -> CountriesList(state.data)
            }
        }
    }
}

@Composable
private fun ErrorState(error: Throwable) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error.message ?: " ")
    }
}

@Composable
private fun IdleState() {}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CountriesList(
    list: List<Country>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) {
            CountryItem(it)
        }
    }
}

@Composable
private fun CountryItem(
    country: Country
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .width(56.dp)
                .border(
                    BorderStroke(1.dp, Color.Black),
                    RectangleShape
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(country.flag)
                .crossfade(true)
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            loading = {
               ShimmerPlaceholder(modifier = Modifier.size(48.dp))
            },
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Text(text = country.name)
    }
}

@Composable
@Preview
fun CountriesListPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        CountriesList(
            list = listOf(
                Country(name = "Russia", flag = "Not found")
            )
        )
    }
}
