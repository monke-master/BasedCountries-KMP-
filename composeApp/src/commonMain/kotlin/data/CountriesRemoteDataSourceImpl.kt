package data

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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class CountriesRemoteDataSourceImpl: CountriesRemoteDataSource {

    override suspend fun getCountries(): List<CountryRemote> {
        val request = GetCountiesRequestBuilder().build()
        val response = httpClient.get(request).body<String>()
        return Json.decodeFromString(ListSerializer(CountrySerializer()), response)
    }

    override suspend fun getCountryByName(name: String): FullCountryRemote {
        val request = GetCountryByNameRequestBuilder().build(name)
        val response = httpClient.get(request).body<String>()
        return Json.decodeFromString(ListSerializer(FullCountrySerializer()), response)[0]
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