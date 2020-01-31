package com.hannah.mynook;

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface GoogleBooksAPIService {

    @GET("volumes")
    fun getBook(@Query("q") book: String): Observable<SearchResponse>

    companion object {
        fun create(): GoogleBooksAPIService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.googleapis.com/books/v1/")
                    .build()

            return retrofit.create(GoogleBooksAPIService::class.java)
        }
    }
}
