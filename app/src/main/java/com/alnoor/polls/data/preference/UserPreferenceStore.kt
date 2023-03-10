package com.alnoor.polls.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alnoor.polls.domain.util.Mapper
import com.alnoor.polls.util.Constant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class UserPreferenceStore(private val context: Context) {

    suspend fun <V> putObject(data: V, key: String, mapper: Mapper<V, String>){
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = mapper.mapTo(data)
        }
    }

    suspend fun <V> getObject(key: String, mapper: Mapper<V, String>): V?{
        return kotlin.run {
            val jsonStr = context.dataStore.data.first()[stringPreferencesKey(key)]

            jsonStr?.let {
                mapper.mapFrom(it)
            }
        }
    }

    suspend fun putPrimitive(key: String, data: String){
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = data
        }
    }

    suspend fun getPrimitive(key: String): String?{
        return context.dataStore.data.first()[stringPreferencesKey(key)]
    }

    suspend fun isLogged(): Boolean{
        val user = context.dataStore.data.first()[stringPreferencesKey(Constant.USER_PREFS_KEY)]
        return user != null
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear()
        }
    }

}