package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.util.FileUtil.convertToRequestBody
import id.andiwijaya.story.domain.model.GenericResult
import id.andiwijaya.story.domain.usecase.post.PostStoryUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddNewStoryViewModel @Inject constructor(
    private val postStoryUseCase: PostStoryUseCase
) : BaseViewModel() {

    private val _genericResult = MutableLiveData<Result<GenericResult>>()
    val genericResult: LiveData<Result<GenericResult>> = _genericResult

    val isButtonEnable = MutableLiveData(false)
    var photo: MultipartBody.Part? = null
    var description = EMPTY_STRING
    var photoFromCameraPath = EMPTY_STRING

    fun postStory() = photo?.let {
        collectFlow(postStoryUseCase(it, description.convertToRequestBody()), _genericResult)
    }

    fun isAllFilled() = listOf(
        photo == null,
        description.isBlank()
    ).contains(true).not()

}