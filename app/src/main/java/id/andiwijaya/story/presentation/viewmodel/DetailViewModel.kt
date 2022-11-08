package id.andiwijaya.story.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.presentation.fragment.DetailFragmentArgs
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

    var story: Story? = null
        private set

    fun processArgs(arguments: DetailFragmentArgs) {
        story = arguments.story
    }

}