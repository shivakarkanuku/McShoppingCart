package com.McDiffyStore.store.repository;

import com.McDiffyStore.store.entity.McUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<McUser,Long> {

    Optional<McUser> findByUsername(String username);
}
