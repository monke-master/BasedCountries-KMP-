package data

interface CountriesRemoteDataSource {

    suspend fun getCountries(): List<CountryRemote>
}