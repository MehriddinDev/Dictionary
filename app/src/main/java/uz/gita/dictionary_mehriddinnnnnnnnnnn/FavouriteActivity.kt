package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class FavouriteActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var dictionaryAdapter: DictionaryAdapterFavourite
    private lateinit var databese: DBHelper
    private lateinit var btnBack: AppCompatImageView
    private lateinit var rvList: RecyclerView
    private lateinit var tts: TextToSpeech
    private lateinit var placeHolder:TextView
    private lateinit var btnFavorite:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        findView()
        clickEvent()

        tts = TextToSpeech(this, this)

        databese = DBHelper.getDatabase()
        val cursor = databese.getFavouriteWords()

        Log.d("TTT","Cursor count = ${cursor.count}")
        if(cursor.count == 0){
            placeHolder.visibility = View.VISIBLE
        }else{
            placeHolder.visibility = View.INVISIBLE
        }

        dictionaryAdapter = DictionaryAdapterFavourite(cursor)
        rvList.adapter = dictionaryAdapter
        rvList.layoutManager = LinearLayoutManager(this)

        //Listener
        dictionaryAdapter.setVolumeListener {
            speakOut(it)
        }

        dictionaryAdapter.setWordTranslateListener {
            val dialog = WordDialog(this,it)
            dialog.show()
        }
    }

    private fun findView() {
        rvList = findViewById(R.id.dictionaryList)
        btnBack = findViewById(R.id.buttonHome)
        placeHolder = findViewById(R.id.placeHolder)
        btnFavorite = findViewById(R.id.btnFavorite)
    }

    private fun clickEvent() {
        btnBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        btnFavorite.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dictionaryAdapter.onDestroy()
    }

    fun upDateAdapter(){
            dictionaryAdapter.cursor = databese.getFavouriteWords()
        dictionaryAdapter.notifyDataSetChanged()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            } else {
            }
        } else {
            Toast.makeText(this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun speakOut(s: String) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}