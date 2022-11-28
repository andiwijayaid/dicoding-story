package id.andiwijaya.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import id.andiwijaya.story.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mapViewModel: MapViewModel
    private val getStoriesUseCase = mock<GetStoriesUseCase>()

    @Before
    fun setUp() {
        mapViewModel = MapViewModel(getStoriesUseCase)
    }

    @Test
    fun `stories should not null`() {
        val expectedData = MutableLiveData<List<Story>>()
        expectedData.value = DataDummy.generateDummyStory()

        whenever(getStoriesUseCase.invokeCache()).thenReturn(expectedData)

        val stories = mapViewModel.getStories()
        verify(getStoriesUseCase).invokeCache()
        verifyNoMoreInteractions(getStoriesUseCase)
        assertThat(stories.value).isNotEmpty()
        stories.value?.forEach {
            assertThat(it.lat).isNotNull()
            assertThat(it.lon).isNotNull()
        }
    }

}