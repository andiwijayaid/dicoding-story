package id.andiwijaya.story.utils

import id.andiwijaya.story.domain.model.Story
import io.github.serpro69.kfaker.Faker

object DataDummy {

    private val faker = Faker()

    fun generateDummyStory(): List<Story> {
        val stories = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                faker.code.asin(),
                faker.name.name(),
                faker.lorem.words(),
                faker.internet.domain(),
                faker.unique.toString(),
                123.123,
                -123.123
            )
            stories.add(story)
        }
        return stories
    }

}