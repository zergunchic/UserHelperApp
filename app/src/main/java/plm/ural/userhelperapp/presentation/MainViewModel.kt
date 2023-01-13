package plm.ural.userhelperapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import plm.ural.userhelperapp.data.MessageListRepositoryImpl
import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessageListController

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = MessageListRepositoryImpl(application)
    private val messageController = MessageListController(repository)

    val messageList = messageController.getMessageList()


    fun deleteMessageItem(messageItem: MessageItem){
        messageController.deleteMessage(messageItem)
    }

    fun changeEnableState(messageItem: MessageItem){
        val newMessage = messageItem.copy(enabled = !messageItem.enabled)
        messageController.editMessage(newMessage)
    }
}