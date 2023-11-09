package uz.gita.dictionary_mehriddinnnnnnnnnnn

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DictionaryAdapter(var cursor: Cursor, var isEng: Boolean, var query: String = "") :
    RecyclerView.Adapter<DictionaryAdapter.MyHolder>() {
    private lateinit var volumeListener: ((String) -> Unit)
    private lateinit var wordTranslateListener: ((WordData) -> Unit)

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
                volumeListener.invoke(cursor.getString(1))
            }
        }

        fun onBind(s: String) {
            textTitle.text = s

            if (isEng) btnVolume.visibility = View.VISIBLE
            else btnVolume.visibility = View.INVISIBLE
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
        val en = cursor.getString(1)
        val uz = cursor.getString(4)
        if (isEng) {
            holder.onBind(en)
        } else {
                holder.onBind(uz)
        }
    }

    fun setVolumeListener(k: ((String) -> Unit)) {
        volumeListener = k
    }

    fun setWordTranslateListener(k: (WordData) -> Unit) {
        wordTranslateListener = k
    }

    fun onDestroy() {
        cursor.close()
    }
}