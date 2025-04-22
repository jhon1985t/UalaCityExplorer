package com.jhonjto.ualacityexplorer.di

import com.jhonjto.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.CityRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCityRepository(
        impl: CityRepositoryImpl
    ): CityRepository
}
