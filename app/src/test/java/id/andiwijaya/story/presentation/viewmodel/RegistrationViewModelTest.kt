package id.andiwijaya.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.model.RegisterResult
import id.andiwijaya.story.domain.usecase.post.RegisterUseCase
import id.andiwijaya.story.utils.MainDispatcherRule
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegistrationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val registrationUseCase = mock<RegisterUseCase>()
    private lateinit var registrationViewModel: RegistrationViewModel
    private val faker = Faker()
    private val mockRegisterRequest = RegisterRequest("", "", "")

    @Before
    fun setUp() {
        registrationViewModel = RegistrationViewModel(registrationUseCase)
    }

    @Test
    fun `when register success`() {
        val expectedResponse = Result.Success(mock<RegisterResult>())

        whenever(registrationUseCase(mockRegisterRequest)).thenReturn(flowOf(expectedResponse))

        registrationViewModel.register()
        verify(registrationUseCase).invoke(mockRegisterRequest)
        verifyNoMoreInteractions(registrationUseCase)
        assertThat(registrationViewModel.registerResult.value).isNotNull()
        assertThat(registrationViewModel.registerResult.value?.status)
            .isEqualTo(Status.SUCCESS)
        assertThat(registrationViewModel.registerResult.value?.data)
            .isEqualTo(expectedResponse.data)
    }

    @Test
    fun `when register error`() {
        val expectedResponse = Result.Error(faker.lorem.words(), null as RegisterResult?, null)

        whenever(registrationUseCase(mockRegisterRequest)).thenReturn(flowOf(expectedResponse))

        registrationViewModel.register()
        verify(registrationUseCase).invoke(mockRegisterRequest)
        verifyNoMoreInteractions(registrationUseCase)
        assertThat(registrationViewModel.registerResult.value).isNotNull()
        assertThat(registrationViewModel.registerResult.value?.status)
            .isEqualTo(Status.ERROR)
        assertThat(registrationViewModel.registerResult.value?.data)
            .isEqualTo(expectedResponse.data)
    }

    @Test
    fun `isAllFilled return false if any empty strings`() {
        assertThat(registrationViewModel.isAllFilled()).isFalse()
    }

    @Test
    fun `isAllFilled return true if there is no empty string`() {
        registrationViewModel.name = faker.name.name()
        registrationViewModel.email = faker.internet.email()
        registrationViewModel.password = faker.lorem.words()
        registrationViewModel.confirmationPassword = faker.lorem.words()
        assertThat(registrationViewModel.isAllFilled()).isTrue()
    }

    @Test
    fun `when isPasswordMatch return true`() {
        faker.lorem.words().apply {
            registrationViewModel.password = this
            registrationViewModel.confirmationPassword = this
        }
        assertThat(registrationViewModel.isPasswordMatch()).isTrue()
    }

    @Test
    fun `when isPasswordMatch return false`() {
        registrationViewModel.password = faker.name.firstName()
        registrationViewModel.confirmationPassword = faker.name.lastName()
        assertThat(registrationViewModel.isPasswordMatch()).isFalse()
    }

}