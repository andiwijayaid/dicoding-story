package id.andiwijaya.story.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.core.StoryDatabase
import id.andiwijaya.story.core.util.orTrue
import id.andiwijaya.story.data.remote.dto.response.toListStory
import id.andiwijaya.story.data.remote.service.StoryRemoteDataSource
import id.andiwijaya.story.domain.model.RemoteKeys
import id.andiwijaya.story.domain.model.Story
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator @Inject constructor(
    private val database: StoryDatabase, private val storyRemoteDataSource: StoryRemoteDataSource
) : RemoteMediator<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Story>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state).let {
                it?.nextKey?.minus(ONE) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                nextKey
            }
        }
        return try {
            val responseData = storyRemoteDataSource.getStories(page, state.config.pageSize)
            val endOfPaginationReached = responseData.data?.listStory?.isEmpty().orTrue()
            database.withTransaction {
                val isError = responseData.data?.error.orTrue()
                if (loadType == LoadType.REFRESH && isError.not()) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.storyDao().deleteAllStory()
                }
                if (isError && database.storyDao().getNumberOfStory() == ZERO) {
                    MediatorResult.Error(Exception("Error"))
                } else {
                    val prevKey = if (page == ONE) null else page - ONE
                    val nextKey = if (endOfPaginationReached) null else page + ONE
                    val keys = responseData.data?.listStory?.map {
                        RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeysDao().insertAll(keys.orEmpty())
                    database.storyDao()
                        .insertStories(responseData.data?.toListStory()?.stories.orEmpty())
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Story>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Story>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Story>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

}