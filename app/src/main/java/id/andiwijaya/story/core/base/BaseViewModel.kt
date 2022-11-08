package id.andiwijaya.story.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun <A> collectFlow(flow: Flow<A>, data: MutableLiveData<A>) = with(viewModelScope) {
        val collector = FlowCollector<A> { value -> data.postValue(value) }
        launch { flow.collect(collector) }
    }

}