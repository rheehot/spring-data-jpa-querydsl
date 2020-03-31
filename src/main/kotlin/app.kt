package jhyun.springDataJpaQueryDsl

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.sql.Timestamp
import java.util.*
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
        var updatedAt: Timestamp? = null,

        @OrderBy("id DESC")
        @JoinColumn(name = "user_id",
                insertable = false, updatable = false)
        @OneToMany(targetEntity = Post::class,
                fetch = FetchType.LAZY,
                cascade = [])
        var posts: List<Post>? = null,

        @OrderBy("id DESC")
        @JoinColumn(name = "user_id",
                insertable = false, updatable = false)
        @OneToMany(targetEntity = Comment::class,
                fetch = FetchType.LAZY,
                cascade = [])
        var comments: List<Comment>? = null

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

interface UserRepository : PagingAndSortingRepository<User, Int> {
    fun findByLoginHandle(loginHandle: String): Optional<User>
}

interface PostRepository : PagingAndSortingRepository<Post, Int>, QuerydslPredicateExecutor<Post>

interface CommentRepository : PagingAndSortingRepository<Comment, Int>

