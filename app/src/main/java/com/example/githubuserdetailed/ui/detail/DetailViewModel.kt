package com.example.githubuserdetailed.ui.detail

import android.content.ContentValues
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Repository
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.dao.AppDatabase
import com.example.githubuserdetailed.dao.DBHelperImpl
import com.example.githubuserdetailed.dao.DbBuilder
import com.example.githubuserdetailed.dao.Favorites
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class DetailViewModel : ViewModel() {
    private var userRepository: Repository = Repository().getInstance()
    var user = MutableLiveData<User>()
    var favoritesLiveData = MutableLiveData<Favorites>()
    lateinit var dbHelper: DBHelperImpl
    fun getUser(username: String?) = liveData(Dispatchers.Default) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepository.getUser(username)))
            user.postValue(userRepository.getUser(username))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Unknown Error Occured!"
                )
            )
        }
    }

    fun setDbHelper(appDatabase: AppDatabase): DBHelperImpl {
        dbHelper = DBHelperImpl(appDatabase)
        return dbHelper
    }

    fun addToFav(username: String?, avatar_url: String?, name: String?) =
        liveData(Dispatchers.Default) {
            var favorites = Favorites(username = username, avatar_url = avatar_url, name = name)
            try {
                emit(Resource.success(dbHelper.addFav(favorites)))
                favoritesLiveData.postValue(dbHelper.getFavbyUsername(username))
            } catch (exception: Exception) {
                Resource.error(
                    data = null,
                    message = exception.message ?: "Unknown Error Occured!"
                )
            }
        }

    fun contentResolver(
        ID: Int?,
        username: String?,
        avatar_url: String?,
        name: String?,
        context: Context
    ) = liveData(Dispatchers.Main) {
        try {
            emit(Resource.success(
                context.contentResolver.insert(
                    Favorites.CONTENT_URI,
                    ContentValues().apply {
                        put(Favorites.ID, ID)
                        put(Favorites.USERNAME, username)
                        put(Favorites.AVATAR_URL, avatar_url)
                        put(Favorites.NAME, name)
                    }
                )
            ))
        } catch (e: java.lang.Exception) {
            Resource.error(
                data = null,
                message = e.message ?: "Unknown Error Occured!"
            )
        }
    }
}