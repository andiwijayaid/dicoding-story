package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : BaseViewModel() {

    val isButtonEnable = MutableLiveData(false)
    var name = EMPTY_STRING
    var email = EMPTY_STRING
    var password = EMPTY_STRING
    var confirmationPassword = EMPTY_STRING

    fun isAllFilled() = listOf(
        name, email, password, confirmationPassword
    ).contains(EMPTY_STRING).not()

    fun isPasswordMatch() = password == confirmationPassword

    fun register() {

    }

}