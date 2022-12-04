package plm.ural.userhelperapp.domain

interface MessagesRepository {
    fun addMessage(message:MessageItem)
    fun deleteMessage(message: MessageItem)
    fun editMessage(messageItem: MessageItem)
    fun getMessage(messageId:Int):MessageItem
    fun getMessageList():List<MessageItem>
}