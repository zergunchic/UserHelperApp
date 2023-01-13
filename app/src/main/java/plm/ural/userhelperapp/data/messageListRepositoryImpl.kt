package plm.ural.userhelperapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessagesRepository
import kotlin.random.Random

class MessageListRepositoryImpl(
    application: Application
):MessagesRepository {
    private val messageListDao = AppDatabase.getInstance(application).messageListDao()
    private val mapper = MessageListMapper()

    override fun addMessage(message: MessageItem) {
        messageListDao.addMessage(mapper.mapEntityToDbModel(message))
    }

    override fun deleteMessage(message: MessageItem) {
        messageListDao.deleteMessage(message.id)
    }

    override fun editMessage(messageItem: MessageItem) {
        messageListDao.addMessage(mapper.mapEntityToDbModel(messageItem))
    }

    override fun getMessage(messageId: Int): MessageItem {
        val dbModel = messageListDao.getMessage(messageId)
        return mapper.mapDBModelToEntity(dbModel)
    }

//    override fun getMessageList(): LiveData<List<MessageItem>> =
//        MediatorLiveData<List<MessageItem>>().apply{
//            addSource(messageListDao.getMessageList()){
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }
// Равно следующему методу
    override fun getMessageList(): LiveData<List<MessageItem>> =
        Transformations.map(messageListDao.getMessageList()){
            mapper.mapListDbModelToListEntity(it)
        }
}