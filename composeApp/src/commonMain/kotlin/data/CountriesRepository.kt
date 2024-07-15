package data

import domain.Country
import domain.FullCountry

interface CountriesRepository {

    suspend fun getCountries(): Result<List<Country>>

    suspend fun getCountryByName(name: String): Result<FullCountry>
}