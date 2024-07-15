package ui.country

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.GetCountryByNameUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CountryViewModel:
    BaseSharedViewModel<CountryState, CountryAction, CountryEvent>(CountryState.Idle), KoinComponent {

    private val getCountryByNameUseCase: GetCountryByNameUseCase by inject()

    override fun obtainEvent(viewEvent: CountryEvent) {
        when (viewEvent) {
            is CountryEvent.LoadData -> loadData(viewEvent.countryName)
        }
    }

    private fun loadData(countryName: String) {
        viewModelScope.launch {
            viewState = CountryState.Loading
            val result = getCountryByNameUseCase.execute(countryName)
            val country = result.getOrElse {
                viewState = CountryState.Error(it)
                return@launch
            }
            viewState = CountryState.Success(country)
        }
    }
}