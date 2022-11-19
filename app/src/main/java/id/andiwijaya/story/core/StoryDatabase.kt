package id.andiwijaya.story.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.andiwijaya.story.core.Constants.Database.DB_NAME
import id.andiwijaya.story.data.local.dao.RemoteKeysDao
import id.andiwijaya.story.data.local.dao.StoryDao
import id.andiwijaya.story.domain.model.RemoteKeys
import id.andiwijaya.story.domain.model.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext, StoryDatabase::class.java, DB_NAME
            ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }

}