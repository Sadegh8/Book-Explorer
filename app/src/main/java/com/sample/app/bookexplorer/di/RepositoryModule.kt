package com.sample.app.bookexplorer.di

import com.sample.app.bookexplorer.data.repository.BookRepositoryImpl
import com.sample.app.bookexplorer.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsBookRepository(repositoryImpl: BookRepositoryImpl): BookRepository
}
