package id.andiwijaya.story.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.presentation.fragment.DetailFragmentArgs
import io.github.serpro69.kfaker.Faker
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private val faker = Faker()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel()
    }

    @Test
    fun `processArgs should set Story from arguments`() {
        val dummy = Story(
            faker.code.asin(),
            faker.name.name(),
            faker.lorem.words(),
            faker.internet.domain(),
            faker.unique.toString()
        )
        detailViewModel.processArgs(DetailFragmentArgs(dummy))
        assertThat(detailViewModel.story).isNotNull()
    }

}