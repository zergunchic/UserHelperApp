package plm.ural.userhelperapp.data

import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessagesRepository

object messageListRepositoryImpl:MessagesRepository {

    private val messageList = mutableListOf<MessageItem>()

    private var autoIncrementId = 0

    override fun addMessage(message: MessageItem) {
        if(message.id == MessageItem.UNDEFINED_ID) {
            message.id = autoIncrementId++
        }
        messageList.add(message)
    }

    override fun deleteMessage(message: MessageItem) {
        messageList.remove(message)
    }

    override fun editMessage(messageItem: MessageItem) {
        val oldMessage = getMessage(messageItem.id)
        messageList.remove(oldMessage)
        addMessage(messageItem)
    }

    override fun getMessage(messageId: Int): MessageItem {
        return messageList.find { it.id == messageId } ?:
        throw RuntimeException("Element with id $messageId not found")
    }

    override fun getMessageList(): List<MessageItem> {
        return messageList.toList()
    }
}