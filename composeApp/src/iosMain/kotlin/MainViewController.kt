import androidx.compose.ui.window.ComposeUIViewController
import data.CountriesRemoteDataSourceImpl
import data.CountriesRepositoryImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ui.navigation.navigationGraph
import utils.log
import utils.provideDispatcher

fun MainViewController() = ComposeUIViewController {
    val odysseyConfiguration = OdysseyConfiguration(
        startScreen = StartScreen.Custom("country_list")
    )

    setNavigationContent(configuration = odysseyConfiguration) {
        navigationGraph()
    }

//    val repo = CountriesRepositoryImpl(CountriesRemoteDataSourceImpl(), provideDispatcher().io)
//    GlobalScope.launch {
//        val res = repo.getCountries()
//        log.d("Heruvim") { res.toString() }
//    }
}