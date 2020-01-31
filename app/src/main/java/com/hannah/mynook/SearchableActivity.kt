package com.hannah.mynook

import android.app.ListActivity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import android.widget.ArrayAdapter
import java.util.*



class SearchableActivity : ListActivity() {

    val googleBooksAPIService by lazy {
        GoogleBooksAPIService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                beginSearch(query)
            }
        }
    }

    private fun beginSearch(searchString: String) {
        disposable = googleBooksAPIService.getBook(searchString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> processResults(result) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun processResults(result: SearchResponse) {
        val list = LinkedList<String>()

        for (i in result.items) {
            list.add(i.volumeInfo.title)
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, list
        )
        setListAdapter(adapter);
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
