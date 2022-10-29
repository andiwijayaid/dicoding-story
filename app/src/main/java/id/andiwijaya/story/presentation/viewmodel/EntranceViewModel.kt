package id.andiwijaya.story.presentation.viewmodel

import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.domain.usecase.load.LoadTokenUseCase
import javax.inject.Inject

@HiltViewModel
class EntranceViewModel @Inject constructor(
    private val loadTokenUseCase: LoadTokenUseCase
) : BaseViewModel() {

    fun isTokenBlank() = loadTokenUseCase().isBlank()

    fun navigate(directions: NavDirections) = goTo(directions)

}