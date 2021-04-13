package com.example.gitfav

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ConsumerFavViewModel : ViewModel() {

    fun setFavList(context: Context) = liveData(Dispatchers.Main) {
        try {
            var res = context.contentResolver.query(FavConsumer.CONTENT_URI, null, null, null, null)
            emit(Resource.success(res))
            Log.i("ContentResolver", "ContentResolver get data: $res")
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = e.message ?: "Unknown Error Occured!"
                )
            )
            Log.e("ContentResolver", "error getting data $e")
        }
    }
}