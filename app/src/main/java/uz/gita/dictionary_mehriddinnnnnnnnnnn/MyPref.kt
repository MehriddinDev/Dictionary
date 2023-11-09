package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.content.Context
import android.content.SharedPreferences

class MyPref {
    val pref: SharedPreferences = App.context.getSharedPreferences("FourPic", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private lateinit var myPref: MyPref

        fun getInstance(): MyPref {
            if (!::myPref.isInitialized) {
                myPref = MyPref()
            }
            return myPref
        }
    }

    fun saveIsEnglish(boolean: Boolean){
        editor.putBoolean("bool",boolean).apply()
    }

    fun getIsEng():Boolean{
        return pref.getBoolean("bool",false)
    }
}