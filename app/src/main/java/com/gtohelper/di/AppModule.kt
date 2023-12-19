package com.gtohelper.di

import android.content.Context
import com.gtohelper.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    @Named("String1")
    fun provideTestString1(
        @ApplicationContext context: Context,
    ) = "${context.getString(R.string.help)}"
}