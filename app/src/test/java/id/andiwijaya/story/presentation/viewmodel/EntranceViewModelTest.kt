package id.andiwijaya.story.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.story.domain.usecase.load.LoadTokenUseCase
import io.github.serpro69.kfaker.Faker
import org.junit.Before
import org.junit.Test

class EntranceViewModelTest {

    private val loadTokenUseCase = mock<LoadTokenUseCase>()
    private lateinit var entranceViewModel: EntranceViewModel
    private val faker = Faker()

    @Before
    fun setUp() {
        entranceViewModel = EntranceViewModel(loadTokenUseCase)
    }

    @Test
    fun `isTokenBlank return false`() {
        whenever(loadTokenUseCase()).thenReturn(faker.lorem.words())

        val actualResult = entranceViewModel.isTokenBlank()
        verify(loadTokenUseCase).invoke()
        verifyNoMoreInteractions(loadTokenUseCase)
        assertThat(actualResult).isFalse()
    }

    @Test
    fun `isTokenBlank return true`() {
        whenever(loadTokenUseCase()).thenReturn("")

        val actualResult = entranceViewModel.isTokenBlank()
        verify(loadTokenUseCase).invoke()
        verifyNoMoreInteractions(loadTokenUseCase)
        assertThat(actualResult).isTrue()
    }

}

