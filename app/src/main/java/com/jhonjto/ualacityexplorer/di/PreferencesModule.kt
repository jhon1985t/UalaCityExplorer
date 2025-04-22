package com.jhonjto.ualacityexplorer.di

import com.jhonjto.data.model.datasource.PreferenceManager
import com.jhonjto.data.model.datasource.PreferencesGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindPreferencesGateway(
        impl: PreferenceManager
    ): PreferencesGateway
}
