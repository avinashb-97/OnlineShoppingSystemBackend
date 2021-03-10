package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying
    @Query("delete from CartItem where id = ?1")
    public void deleteCartItem(Long id);

}
