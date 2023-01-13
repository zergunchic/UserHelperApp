package plm.ural.userhelperapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_items")
data class MessageItemDBModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val userName: String,
    val userMessage:String,
    var enabled: Boolean,
)