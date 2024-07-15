package ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import domain.Country
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ui.LoadingPlaceholder
import ui.ShimmerPlaceholder

@Composable
fun CountriesListScreen() {
    val rootController = LocalRootController.current
    rootController.setupWithViewModels()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        StoredViewModel(factory = { CountriesListViewModel() }) { viewModel ->
            LaunchedEffect(Unit) {
                viewModel.obtainEvent(CountriesListEvent.LoadData)
            }

            val viewState = viewModel.viewStates().observeAsState()
            val viewAction = viewModel.viewActions().observeAsState()

            viewAction.value?.let { action ->
                when (action) {
                    is CountriesListAction.OnCountryClicked -> {
                        rootController.push("country", params = action.name)
                        viewModel.obtainEvent(CountriesListEvent.ActionInvoked)
                    }
                }
            }

            when (val state = viewState.value) {
                is CountriesListState.Error -> ErrorState(state.error)
                CountriesListState.Idle -> IdleState()
                CountriesListState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is CountriesListState.Success -> CountriesList(
                    list = state.data,
                    event = viewModel::obtainEvent
                )
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
fun CountriesList(
    list: List<Country>,
    event: (CountriesListEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) {
            CountryItem(it) { country ->
                event.invoke(CountriesListEvent.CountryClicked(country))
            }
        }
    }
}

@Composable
private fun CountryItem(
    country: Country,
    onClicked: (Country) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.clickable { onClicked(country) }
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
            ),
            event = {}
        )
    }
}
