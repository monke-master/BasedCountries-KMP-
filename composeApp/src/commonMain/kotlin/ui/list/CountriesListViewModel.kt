package ui.list

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.GetCountriesUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.log

class CountriesListViewModel: BaseSharedViewModel<CountriesListState, CountriesListAction, CountriesListEvent>(
    CountriesListState.Idle
), KoinComponent {

    private val getCountriesUseCase: GetCountriesUseCase by inject()

    override fun obtainEvent(viewEvent: CountriesListEvent) {
        when (viewEvent) {
            CountriesListEvent.LoadData -> loadData()
            is CountriesListEvent.CountryClicked -> {
                viewAction = CountriesListAction.OnCountryClicked(viewEvent.country.name)
            }
            is CountriesListEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            viewState = CountriesListState.Loading
            val result = getCountriesUseCase.execute().getOrElse {
                viewState = CountriesListState.Error(it)
                return@launch
            }

            viewState = CountriesListState.Success(result)
        }
    }
}