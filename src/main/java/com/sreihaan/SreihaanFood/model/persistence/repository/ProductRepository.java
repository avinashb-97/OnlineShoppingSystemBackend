package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    String GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL = "select * from product p where (p.category_id = ?1 or p.category_id in (select id from category ac where ac.parent_id = ?1)) ";

    public List<Product> findByIsBestSeller(boolean isBestSeller);

    public List<Product> findByIsFeatured(boolean isFeatured);

    @Query(value = GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, nativeQuery = true)
    public Set<Product> findByAssociatedWithCategory(long categoryId);

    public List<Product> findByNameContaining(String name);

    public List<Product> findByNameContainingAndCategory(String name, Category category);
}
