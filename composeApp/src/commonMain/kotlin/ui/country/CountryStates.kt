package ui.country

import domain.Country

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
        val country: Country
    ): CountryState()

    data object Idle: CountryState()
}