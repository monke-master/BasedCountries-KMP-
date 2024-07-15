package domain

data class FullCountry(
    val name: String,
    val flag: String,
    val capital: String,
    val region: String,
    val area: Float,
    val population: Long,
    val coatOfArms: String
)