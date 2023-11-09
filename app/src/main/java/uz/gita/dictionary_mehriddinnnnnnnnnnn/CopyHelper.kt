package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

open class CopyHelper (
    private val context: Context,
    private val mDatabaseName: String
) : SQLiteOpenHelper(context, mDatabaseName, null, 1) {
    private var mDatabase: SQLiteDatabase? = null

    init {
        if (!isExist()) {
            readableDatabase
            isCopyDatabase()
        }
        openDatabase()
    }

    private fun isExist(): Boolean {
        return context.applicationContext.getDatabasePath(mDatabaseName).exists()
    }

    fun getDatabase(): SQLiteDatabase {
        return mDatabase!!
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.disableWriteAheadLogging()
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun close() {
        mDatabase?.let {
            it.close()
            SQLiteDatabase.releaseMemory()
        }
        super.close()
    }

    fun openDatabase() {
        if (mDatabase != null && mDatabase?.isOpen == true) {
            return
        }
        val path = context.applicationContext.getDatabasePath(mDatabaseName).path
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun isCopyDatabase() {
        val inputStream = context.applicationContext.assets.open(mDatabaseName)  // mDatabase = "Dictionary.db"
        inputStream.use {
            val outputFile = context.applicationContext.getDatabasePath(mDatabaseName).absolutePath
            val outputStream = FileOutputStream(outputFile)
            val buff = ByteArray(1024)
            var length = inputStream.read(buff)
            while (length > 0) {
                outputStream.write(buff, 0, length)
                length = inputStream.read(buff)
            }
            outputStream.flush()
            outputStream.close()
        }
    }
}
