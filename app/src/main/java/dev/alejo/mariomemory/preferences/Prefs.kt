package dev.alejo.mariomemory.preferences

import android.content.Context
import dev.alejo.mariomemory.App.Companion.EASY_DIFFICULT

class Prefs(context: Context) {

    private val storage = context.getSharedPreferences("local_prefs", 0)

    fun saveDifficult(difficultId: Int) {
        storage.edit().putInt("difficult", difficultId).apply()
    }

    fun getDifficult(): Int = storage.getInt("difficult", EASY_DIFFICULT)

    fun wipe() { storage.edit().clear().apply() }

}