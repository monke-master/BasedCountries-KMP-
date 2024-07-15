package ui.country

import domain.Country
import domain.FullCountry

sealed class CountryEvent {
    data class LoadData(
        val countryName: String
    ): CountryEvent()
}

sealed class CountryAction {

}

sealed class CountryState {
    data object Loading: CountryState()

    data class Error(
        val error: Throwable
    ): CountryState()

    data class Success(
        val country: FullCountry
    ): CountryState()

    data object Idle: CountryState()
}