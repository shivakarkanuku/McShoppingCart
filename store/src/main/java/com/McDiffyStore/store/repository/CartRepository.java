package com.McDiffyStore.store.repository;

import com.McDiffyStore.store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("Select cart from Cart cart where cart.user.username=:username")
    Cart findByUsername(@Param("username") String currentUserUsername);

}
