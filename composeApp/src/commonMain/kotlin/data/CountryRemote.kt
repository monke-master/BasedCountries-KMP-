package data

import kotlinx.serialization.Serializable

@Serializable(with = CountrySerializer::class)
data class CountryRemote(
    val name: String,
    val flag: String
)