package plm.ural.userhelperapp.domain

data class MessageItem(
    val id: Int,
    val userName: String,
    val userMessage:String,
    val enabled: Boolean
)
