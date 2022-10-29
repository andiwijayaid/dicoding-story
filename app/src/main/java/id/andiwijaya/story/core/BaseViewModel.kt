package id.andiwijaya.story.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<NavigationCommand>()
    val navigation: LiveData<NavigationCommand> = _navigation

    fun goTo(directions: NavDirections) {
        _navigation.value = NavigationCommand.GoTo(directions)
    }

    fun pop(directions: NavDirections) {
        _navigation.value = NavigationCommand.Pop(directions)
    }

    fun <A> collectFlow(flow: Flow<A>, data: MutableLiveData<A>) = with(viewModelScope) {
        val collector = FlowCollector<A> { value -> data.postValue(value) }
        launch { flow.collect(collector) }
    }

}