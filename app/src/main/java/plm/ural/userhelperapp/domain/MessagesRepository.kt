package plm.ural.userhelperapp.domain

import androidx.lifecycle.LiveData

interface MessagesRepository {
    fun addMessage(message:MessageItem)
    fun deleteMessage(message: MessageItem)
    fun editMessage(messageItem: MessageItem)
    fun getMessage(messageId:Int):MessageItem
    fun getMessageList(): LiveData<List<MessageItem>>
}