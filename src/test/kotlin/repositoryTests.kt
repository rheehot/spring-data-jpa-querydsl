package jhyun.springDataJpaQueryDsl

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.jdbc.Sql


@Sql("classpath:fixture/db/users+posts+comments.sql")
@MySpringDataJpaTest
class UserAndPostRepositoryTest(
        @Autowired
        val userRepository: UserRepository,

        @Autowired
        val postRepository: PostRepository
) {

    @Test
    fun contextLoaded() {
        assertThat(userRepository).isNotNull()
        assertThat(postRepository).isNotNull()
    }

    @Test
    fun findUserByLoginHandle() {
        val poohOpt = userRepository.findByLoginHandle("pooh")
        assertThat(poohOpt.isPresent).isTrue()
    }

    @DisplayName("User의 Posts? (QueryDSL 이용 + PostQueryDslRepository 인터페이스 만들어서 붙이기.")
    @Test
    fun t01() {
        val l = postRepository.listPostByUserId(1)
        assertThat(l).hasSize(2)
        assertThat(l[0].user).isNotNull()
    }

    @DisplayName("Pageable, Sorting")
    @Test
    fun t02() {
        val page = postRepository.pagePostByUserId(1,
                PageRequest.of(0, Int.MAX_VALUE,
                        Sort.by(Sort.Order.desc("title"))))
        assertThat(page.totalElements).isEqualTo(2)
    }

    @DisplayName("흡연 작성자의 포스팅들")
    @Test
    fun t03() {
        val l = postRepository.listPostBySmoker(true)
        assertThat(l).hasSize(1)
        assertThat(l[0].user!!.loginHandle).isEqualTo("CSM")
    }
}