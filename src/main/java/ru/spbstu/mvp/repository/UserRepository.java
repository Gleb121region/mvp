package ru.spbstu.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.request.user.UserUpdateRequest;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserById(Integer id);

    @Modifying
    @Query(
            value = """
                            UPDATE User u SET\s
                            u.firstname = :#{#request.firstName},\s
                            u.lastname = :#{#request.lastName},\s
                            u.aboutMe = :#{#request.aboutMe},\s
                            u.gender = :#{#request.gender},\s
                            u.birthdayDate = :#{#request.birhdayDate},\s
                            u.email = :#{#request.email},\s
                            u.phone = :#{#request.mobile},\s
                            u.linkVK = :#{#request.linkVK}\s
                            WHERE u.id = :userId
                    """
    )
    void updateUser(@Param("userId") Integer userId, @Param("request") UserUpdateRequest request);

    void deleteUserById(Integer userId);
}

