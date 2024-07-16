package data

import domain.Country
import domain.FullCountry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import utils.log

private const val TAG = "CountriesRepositoryImpl"

class CountriesRepositoryImpl(
    private val countriesRemoteDataSource: CountriesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
): CountriesRepository {

    override suspend fun getCountries(): Result<List<Country>> {
        return withContext(dispatcher) {
            try {
                val result = countriesRemoteDataSource.getCountries()
                return@withContext Result.success(result.map { it.toDomain() }.sortedBy { it.name })
            } catch (error: Exception) {
                log.d(tag = TAG) { error.message }
                return@withContext Result.failure(error)
            }
        }
    }

    override suspend fun getCountryByName(name: String): Result<FullCountry> {
        return withContext(dispatcher) {
            try {
                val result = countriesRemoteDataSource.getCountryByName(name)
                return@withContext Result.success(result.toDomain())
            } catch (error: Exception) {
                log.d(tag = TAG) { error.message }
                return@withContext Result.failure(error)
            }
        }
    }
}