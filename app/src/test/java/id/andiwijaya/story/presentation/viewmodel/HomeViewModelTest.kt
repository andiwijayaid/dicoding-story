package id.andiwijaya.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import id.andiwijaya.story.domain.usecase.remove.RemoveTokenUseCase
import id.andiwijaya.story.presentation.util.StoryDiffCallback
import id.andiwijaya.story.utils.DataDummy
import id.andiwijaya.story.utils.MainDispatcherRule
import id.andiwijaya.story.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mianDispatcherRule = MainDispatcherRule()

    private val removeTokenUseCase = mock<RemoveTokenUseCase>()
    private val getStoriesUseCase = mock<GetStoriesUseCase>()
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(removeTokenUseCase, getStoriesUseCase)
    }

    @Test
    fun `when getStories should not null and return success`() = runTest {
        val dummyStories = DataDummy.generateDummyStory()
        val dummyData: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)

        whenever(getStoriesUseCase.invoke()).thenReturn(flowOf(dummyData))

        homeViewModel.getStories()
        val actualStory: PagingData<Story> = homeViewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertThat(differ.snapshot()).isNotNull()
        assertThat(dummyStories).isEqualTo(differ.snapshot())
        assertThat(dummyStories.size).isEqualTo(differ.snapshot().size)
    }

    @Test
    fun `remove token when log out`() {
        homeViewModel.logOut()
        verify(removeTokenUseCase).invoke()
        verifyNoMoreInteractions(removeTokenUseCase)
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>) = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}