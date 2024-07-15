package data

import kotlinx.serialization.Serializable

@Serializable(with = FullCountrySerializer::class)
data class FullCountryRemote(
    val name: String,
    val flag: String,
    val capital: String,
    val region: String,
    val area: Float,
    val population: Long,
    val coatOfArms: String
)