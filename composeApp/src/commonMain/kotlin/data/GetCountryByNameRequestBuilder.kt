package data

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class GetCountryByNameRequestBuilder {

    fun build(name: String): HttpRequestBuilder {
        return request {
            url.takeFrom("$NAME_ENDPOINT/$name")
        }
    }
}