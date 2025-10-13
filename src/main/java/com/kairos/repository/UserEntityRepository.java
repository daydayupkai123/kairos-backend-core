package com.kairos.repository;

import com.kairos.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author kaijiang
 * @date 2025/10/13 10:32
 * @description
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findBYEmail(String email);
}
