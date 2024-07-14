package domain

import data.CountriesRepository

class GetCountriesUseCase(
    private val countriesRepository: CountriesRepository
) {

    suspend fun execute() = countriesRepository.getCountries()
}