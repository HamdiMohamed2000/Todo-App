package com.example.todoapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


enum class SortOrder { BY_NAME, BY_DATE }

data class FilterPreferences(val  sortOrder: SortOrder , val hideCompleted: Boolean)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context:Context) {


    private val dataStore = context.createDataStore("user_preferences")

    val preferancesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.e("HM","Error reading preferences",exception)
                emit(emptyPreferences())
            }else {
                throw exception
            }

        }
        .map { preferences ->
            val sortOrder = SortOrder.valueOf(
                preferences[preferencesKey.SORTE_ORDER] ?: SortOrder.BY_DATE.name
            )
            val hideCompleted = preferences[preferencesKey.HIDE_COMPLETED] ?: false
            FilterPreferences(sortOrder , hideCompleted)

        }
    suspend fun updateStoreOrder(sortOrder: SortOrder){
        dataStore.edit { preferences ->
            preferences[preferencesKey.SORTE_ORDER] = sortOrder.name

        }
    }

    suspend fun updateHideCompleted(hideCompleted : Boolean){
        dataStore.edit {preferences ->
            preferences[preferencesKey.HIDE_COMPLETED] = hideCompleted
        }


    }


    private object preferencesKey {
        val SORTE_ORDER = preferencesKey<String>("sort_order")
        val HIDE_COMPLETED = preferencesKey<Boolean>("hide_compleated")
    }

}

