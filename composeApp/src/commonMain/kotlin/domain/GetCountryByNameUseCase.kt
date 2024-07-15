package domain

import data.CountriesRepository

class GetCountryByNameUseCase(
    private val countriesRepository: CountriesRepository
) {

    suspend fun execute(name: String) = countriesRepository.getCountryByName(name)
}