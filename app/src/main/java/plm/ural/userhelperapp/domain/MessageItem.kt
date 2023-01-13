package plm.ural.userhelperapp.domain

data class MessageItem(
    val userName: String,
    val userMessage:String,
    var enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object {
        const val UNDEFINED_ID = 0
    }
}
