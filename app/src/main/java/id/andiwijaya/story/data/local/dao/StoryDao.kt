package id.andiwijaya.story.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.andiwijaya.story.domain.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<Story>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, Story>

    @Query("DELETE FROM story")
    suspend fun deleteAllStory()

    @Query("SELECT COUNT(*) FROM story")
    suspend fun getNumberOfStory(): Int
}