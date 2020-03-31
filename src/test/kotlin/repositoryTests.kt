package jhyun.springDataJpaQueryDsl

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

// TODO: 특정 User의 Posts? (QueryDSL 이용 + PostQueryDslRepository 인터페이스 만들어서 붙이기.)

@Sql("classpath:fixture/db/users+posts+comments.sql")
@MySpringDataJpaTest
open class UserAndPostRepositoryTest(
        @Autowired
        val userRepository: UserRepository,

        @Autowired
        val postRepository: PostRepository
) {

    @Test
    open fun contextLoaded() {
        assertThat(userRepository).isNotNull()
        assertThat(postRepository).isNotNull()
    }

    @Test
    open fun findUserByLoginHandle() {
        val poohOpt = userRepository.findByLoginHandle("pooh")
        assertThat(poohOpt.isPresent).isTrue()
        val pooh = poohOpt.get()
        assertThat(pooh.posts).hasSize(2)
        assertThat(pooh.comments).hasSize(1)
    }

    @Test
    open fun t01() {
    }

}