package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView

class MenuActivity : AppCompatActivity() {
    private lateinit var btnEng:CardView
    private lateinit var btnUz:CardView
    private lateinit var btnInfo:ImageView
    private val pref = MyPref.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        findView()

        btnEng.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            pref.saveIsEnglish(true)
            startActivity(i)
            finish()
        }

        btnUz.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            pref.saveIsEnglish(false)
            startActivity(i)
            finish()

        }

        btnInfo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Dictionary")
                .setMessage("Ushbu dastur sizga 15 minga yaqin so'zlarni, o'qilishi bilan taqdim qiladi. Dasturda so'zlar Britaniya aksentida o'qiladi.\n\nDasturchi:Mehriddin Sobirov\nGita dasturchilar akademiyasi.")
                .create()
                .show()
        }
    }

    private fun findView(){
        btnEng = findViewById(R.id.btnStartGame)
     btnInfo = findViewById(R.id.btnInfo)
        btnUz = findViewById(R.id.btnScore)
    }

    override fun onBackPressed() {

        finish()
    }
}