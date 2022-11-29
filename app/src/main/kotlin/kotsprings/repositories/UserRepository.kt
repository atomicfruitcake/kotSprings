package kotsprings.repositories

import kotsprings.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<UserModel, Long> {

    @Query("SELECT user FROM UserModel user WHERE user.userName = :userName")
    fun findByUserName(@Param("userName") userName: String): List<UserModel>?
}