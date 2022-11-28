package id.andiwijaya.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status
import id.andiwijaya.story.core.util.FileUtil.toMultiBodyPart
import id.andiwijaya.story.domain.model.GenericResult
import id.andiwijaya.story.domain.usecase.post.PostStoryUseCase
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.flow.flowOf
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class AddNewStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val postStoryUseCase = mock<PostStoryUseCase>()
    private lateinit var addNewStoryViewModel: AddNewStoryViewModel
    private val faker = Faker()

    @Before
    fun setUp() {
        addNewStoryViewModel = AddNewStoryViewModel(postStoryUseCase)
    }

    @Test
    fun `when postStory is success`() {
        val expectedResponse = Result.Success(mock<GenericResult>())

        whenever(
            postStoryUseCase(
                File("").toMultiBodyPart(""),
                "".toRequestBody(),
                "".toRequestBody(),
                "".toRequestBody()
            )
        ).thenReturn(flowOf(expectedResponse))

        addNewStoryViewModel.postStory()
        addNewStoryViewModel.genericResult.value?.let {
            assertThat(it).isNotNull()
            assertThat(it.status).isEqualTo(Status.SUCCESS)
            assertThat(it.data).isEqualTo(expectedResponse.data)
        }
    }

    @Test
    fun `when login error`() {
        val expectedResponse = Result.Error(faker.lorem.words(), null as GenericResult?, null)

        whenever(
            postStoryUseCase(
                File("").toMultiBodyPart(""),
                "".toRequestBody(),
                "".toRequestBody(),
                "".toRequestBody()
            )
        ).thenReturn(flowOf(expectedResponse))

        addNewStoryViewModel.postStory()
        addNewStoryViewModel.genericResult.value?.let {
            assertThat(it).isNotNull()
            assertThat(it.status).isEqualTo(Status.ERROR)
            assertThat(it.data).isEqualTo(expectedResponse.data)
        }
    }

    @Test
    fun `isAllFilled return false if contains true`() {
        assertThat(addNewStoryViewModel.isAllFilled()).isFalse()
    }

    @Test
    fun `isAllFilled return true if not contains true`() = with(addNewStoryViewModel) {
        photo = File("").toMultiBodyPart("")
        description = faker.lorem.words()
        assertThat(isAllFilled()).isTrue()
    }

    @Test
    fun `resetLocationInformation should set lat and lon to null`() = with(addNewStoryViewModel) {
        lat = faker.address.fullAddress()
        lon = faker.address.fullAddress()
        resetLocationInformation()
        assertThat(lat).isNull()
        assertThat(lon).isNull()
    }

    @Test
    fun `setLocationInformation should set value to lat and lon`() = with(addNewStoryViewModel) {
        lat = faker.address.fullAddress()
        lon = faker.address.fullAddress()
        assertThat(lat).isNotNull()
        assertThat(lon).isNotNull()
    }

}