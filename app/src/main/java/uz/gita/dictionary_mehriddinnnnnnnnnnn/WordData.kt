package uz.gita.dictionary_mehriddinnnnnnnnnnn

data class WordData(
    val id:Int,
    val en:String,
    val uz:String,
    val type:String,
    val transcip:String,
    val countable:String,
    var favorite:Int
)