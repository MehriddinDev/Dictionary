package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import uz.gita.dictionary_mehriddinnnnnnnnnnn.databinding.WordDialogBinding


class WordDialog(private var main: AppCompatActivity, private val wordData: WordData) :
    AlertDialog(main) {
    private val database = DBHelper.getDatabase()
    private var binding: WordDialogBinding? = null

    /* private lateinit var WordOrginal: TextView
     private lateinit var WordTrans: TextView
     private lateinit var type: TextView
     private lateinit var countable: TextView
     private lateinit var transcip: TextView
     private lateinit var btnRemember: ImageView
     private lateinit var btnVoice: ImageView*/
    private val myPref = MyPref.getInstance()
    private var isEng = myPref.getIsEng()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WordDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        if (main is FavouriteActivity) main as FavouriteActivity
        describeData(isEng)
        clickEvents()
        window?.setBackgroundDrawable(null)


    }

    /*private fun findView() {
        countable = findViewById(R.id.countable)!!
        WordOrginal = findViewById(R.id.titleEx)!!
        WordTrans = findViewById(R.id.titleTrans)!!
        type = findViewById(R.id.type)!!
        transcip = findViewById(R.id.subTitle)!!
        btnRemember = findViewById(R.id.btnSelect)!!
        btnVoice = findViewById(R.id.btnVolume)!!
    }*/

    private fun clickEvents() {
        with(binding!!) {
            btnSelect.setOnClickListener {

                if (wordData.favorite == 0) {
                    btnSelect.setImageResource(R.drawable.ic_bookmark_select)
                    database.updateWord(1, wordData.id)
                    if (main is MainActivity) {
                        (main as MainActivity).upDateAdapter()
                    } else if (main is FavouriteActivity) {
                        (main as FavouriteActivity)
                        dismiss()
                    }
                    wordData.favorite = 1
                } else {
                    btnSelect.setImageResource(R.drawable.ic_bookmark)
                    database.updateWord(0, wordData.id)
                    if (main is MainActivity) {
                        (main as MainActivity).upDateAdapter()
                    } else if (main is FavouriteActivity) {
                        (main as FavouriteActivity).upDateAdapter()
                        dismiss()
                    }
                    wordData.favorite = 0
                }

            }

            btnVolume.setOnClickListener {
                if (main is MainActivity) {
                    (main as MainActivity).speakOut(wordData.en)
                } else if (main is FavouriteActivity) (main as FavouriteActivity).speakOut(wordData.en)
            }
        }
    }

    fun describeData(isEng: Boolean) {

        if (isEng) {
            binding?.let {
                it.btnVolume.visibility = View.VISIBLE
                it.titleEx.text = wordData.en
                it.titleTrans.text = "1.${wordData.uz}"
                it.TYPE.text = wordData.type
                it.countable.text = wordData.countable
                it.subTitle.text = wordData.transcip
            }
        } else {
            with(binding!!) {
                btnVolume.visibility = View.INVISIBLE
                titleEx.text = wordData.uz
                titleTrans.text = wordData.en
                TYPE.text = wordData.type
            }
        }


        if (wordData.favorite == 1) {
            binding!!.btnSelect.setImageResource(R.drawable.ic_bookmark_select)
        } else {
            binding!!.btnSelect.setImageResource(R.drawable.ic_bookmark)
        }
    }
}