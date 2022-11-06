package id.andiwijaya.story.data.remote.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.core.util.orTrue
import id.andiwijaya.story.data.remote.dto.response.toListStory
import id.andiwijaya.story.domain.model.Story
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StoryPagingSource @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource
) : PagingSource<Int, Story>() {

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? =
        state.closestPageToPosition(state.anchorPosition ?: ZERO)?.prevKey?.plus(ONE)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: ONE
            val response = storyRemoteDataSource.getStories(page)
            if (response.data?.error.orTrue()) {
                LoadResult.Error(Exception("Error"))
            } else LoadResult.Page(
                data = response.data?.toListStory()?.stories ?: listOf(),
                prevKey = if (page == ONE) null else page - ONE,
                nextKey = if (response.data?.listStory?.isEmpty().orTrue()) null else page + ONE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}