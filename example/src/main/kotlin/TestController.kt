package plugin.kotlin.example

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@RestController
class TestController(
    private val testRepository: TestRepository
) {
    @GetMapping("/hello")
    fun hello() =
        Response(data = testRepository.save(TestEntity()))

    data class Response<R>(
        val data: R
    )
}

@Entity
data class TestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val content: String = "This is test"
)

interface TestRepository : JpaRepository<TestEntity, Long>