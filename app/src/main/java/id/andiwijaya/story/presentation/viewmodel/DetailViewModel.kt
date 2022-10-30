package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.usecase.get.GetStoryUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getStoryUseCase: GetStoryUseCase
) : BaseViewModel() {

    private val _story = MutableLiveData<Result<Story>>()
    val story: LiveData<Result<Story>> = _story

    fun getStory(id: String) = collectFlow(getStoryUseCase(id), _story)

}