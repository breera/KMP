package org.breera.project.di

import org.breera.project.app.ShareViewModel
import org.breera.project.book.data.network.KtorRemoteBookDataSource
import org.breera.project.book.data.network.RemoteBookDataSource
import org.breera.project.book.data.network.repository.DefaultBookRepository
import org.breera.project.book.domain.BookRepository
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
}