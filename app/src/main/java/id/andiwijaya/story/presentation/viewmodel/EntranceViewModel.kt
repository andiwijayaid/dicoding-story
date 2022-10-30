package id.andiwijaya.story.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.domain.usecase.load.LoadTokenUseCase
import javax.inject.Inject

@HiltViewModel
class EntranceViewModel @Inject constructor(
    private val loadTokenUseCase: LoadTokenUseCase
) : BaseViewModel() {

    fun isTokenBlank() = loadTokenUseCase().isBlank()

}