package plm.ural.userhelperapp.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun messageListDao():MessageListDao

    //KOTLIN SINGLETON DOUBLE CHECK SYNC
    companion object{
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "message_items.db"
        fun getInstance(application:Application):AppDatabase{
            INSTANCE?.let{
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, AppDatabase::class.java,DB_NAME).build()
                INSTANCE = db
                return db
            }
        }
    }
}