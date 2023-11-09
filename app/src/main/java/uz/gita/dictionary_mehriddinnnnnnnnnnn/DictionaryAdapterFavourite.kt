package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DictionaryAdapterFavourite(var cursor: Cursor) :
    RecyclerView.Adapter<DictionaryAdapterFavourite.MyHolder>() {
    private lateinit var wordTranslateListener: ((WordData) -> Unit)
    private lateinit var volumeListener: ((String) -> Unit)
    val database = DBHelper.getDatabase()

    inner class MyHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle = view.findViewById<TextView>(R.id.title)
        private val btnVolume = view.findViewById<ImageView>(R.id.remember)

        init {
            view.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val id = cursor.getInt(0)
                val en = cursor.getString(1)
                val type = cursor.getString(2)
                val transcript = cursor.getString(3)
                val uz = cursor.getString(4)
                val countable = cursor.getString(5)
                val favorite = cursor.getInt(6)

                wordTranslateListener.invoke(WordData(id,en,uz,type,transcript,countable,favorite))
            }

            btnVolume.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val en = cursor.getString(1)
                volumeListener.invoke(en)
            }
        }

        fun onBind(s: String) {
            textTitle.text = s
            btnVolume.setImageResource(R.drawable.baseline_volume_up_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false)
        )
    }

    override fun getItemCount(): Int = cursor.count

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        cursor.moveToPosition(position)
        val id = cursor.getInt(0)
        val en = cursor.getString(1)
        holder.onBind(en)
    }

    fun setWordTranslateListener(k: ((WordData) -> Unit)) {
        wordTranslateListener = k
    }

    fun setVolumeListener(k: ((String) -> Unit)) {
        volumeListener = k
    }

    fun onDestroy() {
        cursor.close()
    }
}