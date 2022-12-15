package plm.ural.userhelperapp.domain

class MessageItemController(private val repository: MessagesRepository) {
    fun getMessageItem(id:Int):MessageItem{
        return repository.getMessage(id)
    }

    fun addMessageItem(messageItem: MessageItem){
        repository.addMessage(messageItem)
    }

    fun editMessageItem(messageItem: MessageItem){
        repository.editMessage(messageItem)
    }
}