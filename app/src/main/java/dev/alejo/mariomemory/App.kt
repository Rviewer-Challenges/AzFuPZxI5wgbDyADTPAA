package dev.alejo.mariomemory

import android.app.Application

class App: Application() {

    companion object {
        const val EASY_DIFFICULT = 0
        const val MEDIUM_DIFFICULT = 1
        const val HARD_DIFFICULT = 2
        const val EASY_COLUMNS = 4
        const val MEDIUM_COLUMNS = 6
        const val HARD_COLUMNS = 6
    }

}