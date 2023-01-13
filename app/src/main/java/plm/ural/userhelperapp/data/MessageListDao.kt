package plm.ural.userhelperapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import plm.ural.userhelperapp.domain.MessageItem

@Dao
interface MessageListDao {
    @Query("SELECT * FROM message_items")
    fun getMessageList(): LiveData<List<MessageItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessage(messageItemDBModel: MessageItemDBModel)

    @Query("DELETE FROM message_items WHERE id=:messageItemId")
    fun deleteMessage(messageItemId: Int)

    @Query("SELECT * FROM message_items WHERE id:messageItemId LIMIT 1")
    fun getMessage(messageItemId: Int):MessageItemDBModel
}