package org.breera.project.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.breera.project.app.ShareViewModel
import org.breera.project.book.data.database.DatabaseFactory
import org.breera.project.book.data.database.FavouriteBookDatabase
import org.breera.project.book.data.network.KtorRemoteBookDataSource
import org.breera.project.book.data.network.RemoteBookDataSource
import org.breera.project.book.data.repository.DefaultBookRepository
import org.breera.project.book.domain.BookRepository
import org.breera.project.book.presentation.book_detail.components.BookDetailViewModel
import org.breera.project.book.presentation.book_list.BookListViewModel
import org.breera.project.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    viewModelOf(::BookListViewModel)
    viewModelOf(::ShareViewModel)
    viewModelOf(::BookDetailViewModel)
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .addMigrations()
            .build()
    }

    single { get<FavouriteBookDatabase>().dao }
}