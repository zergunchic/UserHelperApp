package plm.ural.userhelperapp.domain

import androidx.lifecycle.LiveData

class MessageListController(private val messageRepository:MessagesRepository) {
    fun getMessageList(): LiveData<List<MessageItem>> {
        return messageRepository.getMessageList()
    }

    fun getMessageById(id: Int){
        messageRepository.getMessage(id)
    }

    fun createMessage(messageItem: MessageItem ){
        messageRepository.addMessage(messageItem)
    }

    fun editMessage(message: MessageItem){
        messageRepository.editMessage(message)
    }
    fun deleteMessage(messageItem: MessageItem){
        messageRepository.deleteMessage(messageItem)
    }

}