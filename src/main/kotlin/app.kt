package jhyun.springDataJpaQueryDsl

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.PagingAndSortingRepository
import java.sql.Timestamp
import javax.persistence.*


@SpringBootApplication
open class App

fun main() {
    runApplication<App>()
}

/* DB Entities -------------------------------------------- */

@Table(name = "users")
@Entity
data class User(
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        @Id
        val id: Int? = null,

        @Column(name = "login_handle", nullable = false)
        val loginHandle: String? = null,

        @CreationTimestamp
        @Column(name = "created_at", nullable = false)
        var createdAt: Timestamp? = null,

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        var updatedAt: Timestamp? = null
)

@Table(name = "posts")
@Entity
data class Post(
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        @Id
        val id: Int? = null,

        @Column(name = "user_id", nullable = false)
        val userId: Int? = null,

        @JoinColumn(name = "user_id", referencedColumnName = "id",
                insertable = false, updatable = false)
        @ManyToOne(targetEntity = User::class)
        var user: User? = null,

        @Column(name = "title", nullable = false)
        val title: String? = null,

        @Column(name = "content", nullable = false)
        val content: String? = null,

        @CreationTimestamp
        @Column(name = "created_at", nullable = false)
        var createdAt: Timestamp? = null,

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        var updatedAt: Timestamp? = null
)


@Table(name = "comments")
@Entity
data class Comment(
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        @Id
        val id: Int? = null,

        @Column(name = "post_id", nullable = false)
        val postId: Int? = null,

        @Column(name = "user_id", nullable = false)
        val userId: Int? = null,

        @JoinColumn(name = "user_id", referencedColumnName = "id",
                insertable = false, updatable = false)
        @ManyToOne(targetEntity = User::class)
        var user: User? = null,

        @JoinColumn(name = "post_id", referencedColumnName = "id",
                insertable = false, updatable = false)
        @ManyToOne(targetEntity = Post::class)
        var post: Post? = null,

        @Column(name = "comment", nullable = false)
        val comment: String? = null,

        @CreationTimestamp
        @Column(name = "created_at", nullable = false)
        var createdAt: Timestamp? = null
)

/* DB Repositories -------------------------------------------- */

interface UserRepository : PagingAndSortingRepository<User, Int>

interface PostRepository : PagingAndSortingRepository<Post, Int>

interface CommentRepository : PagingAndSortingRepository<Comment, Int>

// TODO: 특정 User의 Posts? (QueryDSL 이용 + PostQueryDslRepository 인터페이스 만들어서 붙이기.)

// TODO: @Sql + Jupiter test (Repository) : User, Post, Comment 모두 fixtures 입력. JoinColumn 동작 확인.

