package data

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class GetCountiesRequestBuilder {

    private val defaultFields = listOf(NAME_FIELD, FLAGS_FIELD)

    fun build(): HttpRequestBuilder {
        return request {
            url.takeFrom(ALL_ENDPOINT)
            parameter(FIELD_PARAM, defaultFields.joinToString(separator = ","))
        }
    }
}