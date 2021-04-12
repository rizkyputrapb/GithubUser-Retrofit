package com.example.githubuserdetailed.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.dao.AppDatabase
import com.example.githubuserdetailed.dao.DBHelperImpl
import com.example.githubuserdetailed.dao.Favorites
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FavoriteViewModel : ViewModel() {
    lateinit var dbHelper: DBHelperImpl
    var favorites = MutableLiveData<List<Favorites>>()
    fun setDbHelper(appDatabase: AppDatabase): DBHelperImpl {
        dbHelper = DBHelperImpl(appDatabase)
        return dbHelper
    }

    fun getFavorites() = liveData(Dispatchers.Default) {
        try {
            emit(Resource.success(dbHelper.getFavorites()))
            favorites.postValue(dbHelper.getFavorites())
        } catch (e: Exception) {
            Resource.error(
                data = null,
                message = e.message ?: "Unknown Error Occured!"
            )
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
}