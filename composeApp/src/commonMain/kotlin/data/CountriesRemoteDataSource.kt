package data

interface CountriesRemoteDataSource {

    suspend fun getCountries(): List<CountryRemote>

    suspend fun getCountryByName(name: String): CountryRemote
}