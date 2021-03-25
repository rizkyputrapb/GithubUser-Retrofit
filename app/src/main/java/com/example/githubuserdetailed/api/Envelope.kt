package com.example.githubuserdetailed.api

data class Envelope<T>(var total_count: Int, var incomplete_results: Boolean, var items: T)