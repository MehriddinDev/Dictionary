package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

/*
class DBHelper(private val context: Context) :CopyHelper(context,"Mehriddin.db") {
    companion object{
        @SuppressLint("StaticFieldLeak")
        private var dbHelper:DBHelper? = null

        fun getIsctance(context: Context):DBHelper?{
            if (dbHelper == null)
                dbHelper = DBHelper(context)
            return dbHelper
        }
    }

    private lateinit var database: SQLiteDatabase
    init {
        val file = context.getDatabasePath("dictionary.db")
        if (!file.exists())
            copyToLocal()

        database = SQLiteDatabase.openDatabase(file.absolutePath,null,SQLiteDatabase.OPEN_READWRITE)
    }


    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    private fun copyToLocal() {
        val inputStream = context.assets.open("data.db")
        val file = context.getDatabasePath("dictionary.db")
        val fileOutputStream = FileOutputStream(file)
        try {
            val byte = ByteArray(1024)
            var length = 0
            while (inputStream.read(byte).also { length = it } > 0) {
                fileOutputStream.write(byte, 0, length)
            }
            fileOutputStream.flush()
        } catch (e: Exception) {
            file.delete()
        } finally {
            inputStream.close()
            fileOutputStream.close()
        }
    }

    fun getAll():Cursor{
        return database.rawQuery("SELECT * FROM dictionary",null)
    }

    fun getWordsByQuery(query:String):Cursor{
        return database.rawQuery("SELECT * FROM dictionary WHERE english LIKE ?",arrayOf(query)
        )
    }


}*/

class DBHelper private constructor(context: Context) : CopyHelper(context, "data.db") {
    companion object {
        private var database: DBHelper? = null
        fun init(context: Context) {
            database = DBHelper(context)
        }

        fun getDatabase(): DBHelper = database!!
    }



    /*fun getWordByQueryEnglish(query: String): Cursor {
        return getDatabase().rawQuery(
            "SELECT * FROM dictionary WHERE dictionary.english LIKE '%$query%' AND dictionary.english GLOB '[a-zA-Z]*' ORDER BY english ASC", null
        )
    }*/

    fun getWordByQueryEnglish(query: String): Cursor {
        val sanitizedQuery = query.replace("'", "''")
        val sqlQuery = "SELECT * FROM dictionary WHERE dictionary.english LIKE '%$sanitizedQuery%' AND dictionary.english GLOB '[a-zA-Z]*' ORDER BY english ASC"
        return getDatabase().rawQuery(sqlQuery, null)
    }

    fun getWordByQueryUzbek(query: String): Cursor {
        val sanitizedQuery = query.replace("'", "''")
        val sqlQuery = "SELECT * FROM dictionary WHERE dictionary.uzbek LIKE '%$sanitizedQuery%' AND dictionary.uzbek GLOB '[a-zA-Z]*' ORDER BY uzbek ASC"
        return getDatabase().rawQuery(sqlQuery, null)
        //SELECT * FROM dictionary WHERE dictionary.english LIKE '%$query%' AND dictionary.english GLOB '[a-zA-Z]*' ORDER BY english ASC
    }

    fun updateWord(remember: Int, id: Int) {
        val cv = ContentValues()
        cv.put("favourite", remember)
        val k = getDatabase().update("dictionary", cv, "dictionary.id == '$id'", null)
        Log.d("XXX","database = $k")
    }

    fun getFavouriteWords():Cursor{
        return getDatabase().rawQuery("select * from dictionary where dictionary.favourite == '1'", null)
    }

    fun getUzAllWord():Cursor{
        return getDatabase().rawQuery("SELECT * FROM dictionary where uzbek GLOB '[a-zA-Z]*' ORDER BY UPPER(uzbek)",null)
    }

    fun getEnglishAllWord(): Cursor {
        val cursor = getDatabase().rawQuery("SELECT * FROM dictionary", null)
        return cursor
    }
}