package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var searchView: SearchView
    private lateinit var rvList: RecyclerView
    private lateinit var dictionaryAdapter: DictionaryAdapter
    private lateinit var databese: DBHelper
    private lateinit var card: CardView
    private lateinit var handler: Handler
    private lateinit var btnShuffle: ImageButton
    private lateinit var tts: TextToSpeech
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnBack: AppCompatImageView
    private var isEng = false
    private val pref = MyPref.getInstance()
    private lateinit var txtLanguage : TextView

    private var _query = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()
        clickEvent()
        databese = DBHelper.getDatabase()
        tts = TextToSpeech(this, this)

        isEng = pref.getIsEng()

        if (isEng) {
            txtLanguage.setText("English - Uzbek")
            dictionaryAdapter = DictionaryAdapter(databese.getEnglishAllWord(), isEng)
        } else {
            txtLanguage.setText("Uzbek - English")
            dictionaryAdapter = DictionaryAdapter(databese.getUzAllWord(), isEng)
        }

        rvList.adapter = dictionaryAdapter
        rvList.layoutManager = LinearLayoutManager(this)
        handler = Handler(Looper.getMainLooper())

        // Listeners
        dictionaryAdapter.setVolumeListener {
            speakOut(it)
        }

        dictionaryAdapter.setWordTranslateListener {
            val dialog = WordDialog(this, it)
            dialog.show()
        }
        ////////////

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                Log.d("YYY", "query = $query")
                query?.let {
                    if (isEng) {
                        dictionaryAdapter.cursor = databese.getWordByQueryEnglish(it.trim())
                    } else {
                        dictionaryAdapter.cursor = databese.getWordByQueryUzbek(it.trim())

                    }
                    dictionaryAdapter.query = it.trim()
                    _query = it.trim()
                    dictionaryAdapter.notifyDataSetChanged()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    Log.d("YYY", "query = $newText")
                    newText?.let {
                        if (isEng) {
                            dictionaryAdapter.cursor = databese.getWordByQueryEnglish(it.trim())
                        } else {
                            dictionaryAdapter.cursor = databese.getWordByQueryUzbek(it.trim())

                        }
                        dictionaryAdapter.query = it.trim()
                        _query = it.trim()
                        dictionaryAdapter.notifyDataSetChanged()
                    }

                }, 200)

                return true
            }
        })

    }

    private fun findView() {
        rvList = findViewById(R.id.dictionaryList)
        btnBack = findViewById(R.id.buttonHome)
        searchView = findViewById(R.id.searchView)
        btnShuffle = findViewById(R.id.btnShuffle)
        card = findViewById(R.id.linear)
        txtLanguage = findViewById(R.id.txtLanguage)
        btnFavorite = findViewById(R.id.btnFavorite)
    }

    private fun clickEvent() {
        btnShuffle.setOnClickListener {
            if (isEng) {
                dictionaryAdapter.cursor = databese.getUzAllWord()
                dictionaryAdapter.isEng = false
                dictionaryAdapter.notifyDataSetChanged()
                isEng = false
                pref.saveIsEnglish(false)

                txtLanguage.setText("Uzbek - English")
            } else {
                dictionaryAdapter.cursor = databese.getEnglishAllWord()
                dictionaryAdapter.isEng = true
                dictionaryAdapter.notifyDataSetChanged()
                isEng = true
                pref.saveIsEnglish(true)
                txtLanguage.setText("English - Uzbek")
            }
        }

        btnFavorite.setOnClickListener {
            val i = Intent(this, FavouriteActivity::class.java)
            startActivity(i)
            finish()
        }
        btnBack.setOnClickListener {
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    fun speakOut(s: String) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        dictionaryAdapter.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            } else {
            }
        } else {
            Toast.makeText(this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun upDateAdapter() {
        if (isEng) {
            dictionaryAdapter.cursor = databese.getEnglishAllWord()
        } else {
            dictionaryAdapter.cursor = databese.getUzAllWord()
        }
    }

    override fun onBackPressed() {
        val i = Intent(this, MenuActivity::class.java)
        startActivity(i)
        finish()
    }
}