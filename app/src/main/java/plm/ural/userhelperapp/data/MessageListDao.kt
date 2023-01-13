package plm.ural.userhelperapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageListDao {
    //Room получает объеты Live Data в отдельном потоке и не блокирует UI
    @Query("SELECT * FROM message_items")
    fun getMessageList(): LiveData<List<MessageItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessage(messageItemDBModel: MessageItemDBModel)

    @Query("DELETE FROM message_items WHERE id=:messageItemId")
    fun deleteMessage(messageItemId: Int)

    @Query("SELECT * FROM message_items WHERE id=:messageItemId LIMIT 1")
    fun getMessage(messageItemId: Int):MessageItemDBModel
}