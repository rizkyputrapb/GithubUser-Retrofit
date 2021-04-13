package com.example.githubuserdetailed.ui.favorites

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.dao.AppDatabase
import com.example.githubuserdetailed.dao.DBHelperImpl
import com.example.githubuserdetailed.dao.Favorites
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FavoriteViewModel : ViewModel() {
    lateinit var dbHelper: DBHelperImpl
    private val _navigatetoDetail = MutableLiveData<User?>()
    fun setDbHelper(appDatabase: AppDatabase): DBHelperImpl {
        dbHelper = DBHelperImpl(appDatabase)
        return dbHelper
    }

    fun setFavoriteList(context: Context) = liveData(Dispatchers.IO) {
        try {
            var res = context.contentResolver.query(Favorites.CONTENT_URI, null, null, null, null)
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

    fun deleteFav(username: String) = liveData(Dispatchers.Default) {
        try {
            emit(Resource.success((dbHelper.deleteFav(username))))
        } catch (e: Exception) {
            Resource.error(
                data = null,
                message = e.message ?: "Unknown Error Occured!"
            )
        }
    }

    fun navigatetoDetail(): LiveData<User?> {
        return _navigatetoDetail
    }

    fun onUserClicked(user: User?) {
        _navigatetoDetail.value = user
    }

    fun onUserMainDetailNavigated() {
        _navigatetoDetail.value = null
    }
}