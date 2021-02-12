package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    String GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL = "select p.* from product where (pc.categoryid = ?1 or pc.categoryid in (select ac.id from category where ac.parentid = ?1)) ";

    public List<Product> findByIsBestSeller(boolean isBestSeller);

    public List<Product> findByIsFeatured(boolean isFeatured);

    @Query(value = GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, nativeQuery = true)
    List<Product> findByAssociatedWithCategory(long id);
}
