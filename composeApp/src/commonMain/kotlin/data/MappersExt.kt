package data

import domain.Country
import domain.FullCountry

internal fun CountryRemote.toDomain(): Country {
    return Country(name = name, flag = flag)
}

internal fun FullCountryRemote.toDomain(): FullCountry {
    return FullCountry(
        name = name,
        flag = flag,
        capital = capital,
        region = region,
        area = area,
        population = population,
        coatOfArms = coatOfArms
    )
}