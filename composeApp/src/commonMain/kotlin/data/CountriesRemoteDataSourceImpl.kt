package data

import domain.Country
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CountriesRemoteDataSourceImpl: CountriesRemoteDataSource {

    override suspend fun getCountries(): List<CountryRemote> {
        val request = GetCountiesRequestBuilder().build()
        return httpClient.get(request).body<List<CountryRemote>>()
    }

    override suspend fun getCountryByName(name: String): CountryRemote {
        val request = GetCountryByNameRequestBuilder().build(name)
        return httpClient.get(request).body<List<CountryRemote>>()[0]
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url(BASE_URL)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}