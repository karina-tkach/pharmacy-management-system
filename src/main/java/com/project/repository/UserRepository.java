package com.project.repository;

import com.project.entity.user.Role;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName, u.email=:email WHERE u.id=:id")
    void updateUser(@Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("email") String email,
                    @Param("id") Long id);

    List<User> findAllByRole(Role role);
}
