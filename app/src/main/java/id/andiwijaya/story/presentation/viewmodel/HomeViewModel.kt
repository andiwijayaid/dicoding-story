package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import id.andiwijaya.story.domain.usecase.remove.RemoveTokenUseCase
import id.andiwijaya.story.presentation.fragment.HomeFragmentDirections
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel() {

    private val _stories = MutableLiveData<PagingData<Story>>()
    val stories: LiveData<PagingData<Story>> = _stories

    fun getStories(page: Int, size: Int? = null, location: Int? = null) = collectFlow(
        getStoriesUseCase(page, size, location), _stories
    )

    fun logOut() {
        removeTokenUseCase.invoke()
        pop(HomeFragmentDirections.actionHomeToLogin())
    }

    fun navigateToDetail(id: String) = goTo(HomeFragmentDirections.actionHomeToDetail(id))

}