package com.hannah.mynook

data class SearchResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)