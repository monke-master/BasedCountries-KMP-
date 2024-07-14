package data

import domain.Country

internal fun CountryRemote.toDomain(): Country {
    return Country(name = name, flag = flag)
}