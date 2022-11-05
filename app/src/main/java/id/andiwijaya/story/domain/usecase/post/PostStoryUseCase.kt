package id.andiwijaya.story.domain.usecase.post

import id.andiwijaya.story.domain.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostStoryUseCase @Inject constructor(private val repository: StoryRepository) {
    operator fun invoke(photo: MultipartBody.Part, description: RequestBody) =
        repository.postStory(photo, description)
}