package ru.spbstu.mvp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.User;
import ru.spbstu.mvp.request.user.UserUpdateRequest;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    Optional<User> findByEmail(String email);

    @Transactional
    Optional<User> findUserById(Integer id);

    @Modifying
    @Transactional
    @Query(value = """
                                UPDATE User u SET
                                u.password = :password
                                WHERE u.id = :userId
            """)
    void changeUserPassword(@Param("userId") Integer userId, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = """
                    UPDATE User u SET
                    u.firstname = :#{#request.firstname()},
                    u.lastname = :#{#request.lastname()},
                    u.aboutMe = :#{#request.about()},
                    u.gender = :#{#request.gender()},
                    u.birthdayDate = :#{#request.birthdayDate()},
                    u.phone = :#{#request.phone()}
                    WHERE u.id = :userId
            """)
    void updateUser(@Param("userId") Integer userId, @Param("request") UserUpdateRequest request);

    @Transactional
    void deleteUserById(Integer userId);
}

