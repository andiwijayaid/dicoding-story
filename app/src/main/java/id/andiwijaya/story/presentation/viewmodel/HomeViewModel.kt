package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import id.andiwijaya.story.domain.usecase.remove.RemoveTokenUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel() {

    private val _stories = MutableLiveData<PagingData<Story>>()
    val stories: LiveData<PagingData<Story>> = _stories

    init {
        getStories(ONE)
    }

    fun getStories(page: Int, size: Int? = null, location: Int? = null) = collectFlow(
        getStoriesUseCase(page, size, location).cachedIn(viewModelScope), _stories
    )

    fun logOut() = removeTokenUseCase.invoke()

}