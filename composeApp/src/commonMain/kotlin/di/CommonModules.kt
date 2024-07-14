package di

import data.CountriesRemoteDataSource
import data.CountriesRemoteDataSourceImpl
import data.CountriesRepository
import data.CountriesRepositoryImpl
import domain.GetCountriesUseCase
import org.koin.dsl.module

private val dataModule = module {
    single<CountriesRemoteDataSource> {
        CountriesRemoteDataSourceImpl()
    }

    single<CountriesRepository> {
        CountriesRepositoryImpl(get())
    }
}

private val domainModule = module {
    single<GetCountriesUseCase> {
        GetCountriesUseCase(get())
    }
}

val commonModules = listOf(
    dataModule,
    domainModule
)