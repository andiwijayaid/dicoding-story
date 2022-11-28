package id.andiwijaya.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.usecase.post.LoginUseCase
import id.andiwijaya.story.utils.MainDispatcherRule
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase = mock<LoginUseCase>()
    private lateinit var loginViewModel: LoginViewModel
    private val faker = Faker()
    private val mockLoginRequest = LoginRequest("", "")

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `when login success`() {
        val expectedResponse = Result.Success(mock<LoginResult>())

        whenever(loginUseCase(mockLoginRequest)).thenReturn(flowOf(expectedResponse))

        loginViewModel.login()
        verify(loginUseCase).invoke(mockLoginRequest)
        verifyNoMoreInteractions(loginUseCase)
        assertThat(loginViewModel.loginResult.value).isNotNull()
        assertThat(loginViewModel.loginResult.value?.status).isEqualTo(Status.SUCCESS)
        assertThat(loginViewModel.loginResult.value?.data).isEqualTo(expectedResponse.data)
    }

    @Test
    fun `when login error`() {
        val expectedResponse = Result.Error(faker.lorem.words(), null as LoginResult?, null)

        whenever(loginUseCase(mockLoginRequest)).thenReturn(flowOf(expectedResponse))

        loginViewModel.login()
        verify(loginUseCase).invoke(mockLoginRequest)
        verifyNoMoreInteractions(loginUseCase)
        assertThat(loginViewModel.loginResult.value).isNotNull()
        assertThat(loginViewModel.loginResult.value?.status).isEqualTo(Status.ERROR)
        assertThat(loginViewModel.loginResult.value?.data).isEqualTo(expectedResponse.data)
    }

    @Test
    fun `when isAllFilled return true`() {
        loginViewModel.email = faker.internet.email()
        loginViewModel.password = faker.lorem.words()
        assertThat(loginViewModel.isAllFilled()).isTrue()
    }

    @Test
    fun `isAllFilled return false when email is empty string`() {
        loginViewModel.email = faker.internet.email()
        assertThat(loginViewModel.isAllFilled()).isFalse()
    }

    @Test
    fun `isAllFilled return false when password is empty string`() {
        loginViewModel.password = faker.lorem.words()
        assertThat(loginViewModel.isAllFilled()).isFalse()
    }

    @Test
    fun `isAllFilled return false when email and password is empty string`() {
        assertThat(loginViewModel.isAllFilled()).isFalse()
    }

}