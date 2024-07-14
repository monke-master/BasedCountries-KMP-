package ui

import domain.Country

sealed class CountriesListEvent {
    data object LoadData: CountriesListEvent()
}

sealed class CountriesListAction {
    data class OnCountryClicked(
        val name: String
    ): CountriesListAction()
}

sealed class CountriesListState {
    data object Loading: CountriesListState()

    data class Error(
        val error: Throwable
    ): CountriesListState()

    data object Idle: CountriesListState()

    data class Success(
        val data: List<Country>
    ): CountriesListState()

}