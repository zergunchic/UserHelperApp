package plm.ural.userhelperapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessagesRepository
import kotlin.random.Random

object MessageListRepositoryImpl:MessagesRepository {

    private val messageList = sortedSetOf<MessageItem>({ o1,o2 -> o1.id.compareTo(o2.id)} )
    private val messageListLD = MutableLiveData<List<MessageItem>>()
    private var autoIncrementId = 0

    override fun addMessage(message: MessageItem) {
        if(message.id == MessageItem.UNDEFINED_ID) {
            message.id = autoIncrementId++
        }
        messageList.add(message)
        updateList()
    }

    override fun deleteMessage(message: MessageItem) {
        messageList.remove(message)
        updateList()
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

    override fun getMessageList(): LiveData<List<MessageItem>> {
        return messageListLD
    }

    private fun updateList(){
        messageListLD.value = messageList.toList()
    }
}