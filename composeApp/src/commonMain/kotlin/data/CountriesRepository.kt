package data

import domain.Country

interface CountriesRepository {

    suspend fun getCountries(): Result<List<Country>>

    suspend fun getCountryByName(name: String): Result<Country>
}