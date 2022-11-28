package id.andiwijaya.story.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel() {
    fun getStories() = getStoriesUseCase.invokeCache()
}