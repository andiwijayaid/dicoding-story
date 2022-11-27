package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel() {

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>> = _stories

    fun geStories() {
        _stories.value = getStoriesUseCase.invokeCache().value
    }

}