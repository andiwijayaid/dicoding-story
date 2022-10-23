package id.andiwijaya.story.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.domain.usecase.remove.RemoveTokenUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase
) : BaseViewModel() {

    fun logOut() {
        removeTokenUseCase.invoke()
    }

}