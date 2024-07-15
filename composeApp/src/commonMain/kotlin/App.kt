import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.list.CountriesListScreen


@Composable
@Preview
fun App() {
    MaterialTheme {
        CountriesListScreen()
    }
}