package plm.ural.userhelperapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import plm.ural.userhelperapp.data.MessageListRepositoryImpl
import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessageListController

class MainViewModel: ViewModel() {

    private val repository = MessageListRepositoryImpl
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