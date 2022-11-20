package id.andiwijaya.story.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.domain.usecase.get.GetStoriesUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    getStoriesUseCase: GetStoriesUseCase
) : BaseViewModel() {

    val stories = getStoriesUseCase.invokeCache()

}