package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.CategoryNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.repository.CategoryRepository;
import com.sreihaan.SreihaanFood.model.persistence.repository.ProductRepository;
import com.sreihaan.SreihaanFood.service.CategoryService;
import com.sreihaan.SreihaanFood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found -> Name : "+name));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found -> Id : "+id));
    }

    @Override
    public Category updateCategoryDetails(long categoryId, Category categoryObj) {
        Category category = getCategoryById(categoryId);
        category.setName(categoryObj.getName());
        category.setDescription(categoryObj.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryDetails(Category category)
    {
        return categoryRepository.save(category);
    }

//    @Override
//    public Set<Product> getProductsForCategory(long id) {
//        Category category = getCategoryById(id);
//        return category.getProducts();
//    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public boolean isChildCategory(Category category, Category parent) {
        return category.getParent().equals(parent);
    }

    @Override
    public Category createAndAddSubCategory(Long parentId, Category subCategory) {
        Category parent = getCategoryById(parentId);
        subCategory.setParent(parent);
        checkIsParentAndChildCategory(subCategory, parent);
        return categoryRepository.save(subCategory);
    }

    @Override
    public Category updateSubCategoryDetails(Long parentId, Long childId, Category subCategory) {
        Category parent = getCategoryById(parentId);
        Category child = getCategoryById(childId);
        if(!isChildCategory(child, parent))
        {
            throw new IllegalArgumentException("category " + parent.getId() + " is not parent of " + child.getId());
        }
        return categoryRepository.save(child);
    }

    @Override
    public void deleteSubCategory(long parentId, long subCategoryId) {
        Category parent = getCategoryById(parentId);
        Category child = getCategoryById(subCategoryId);
        checkIsParentAndChildCategory(child, parent);
        categoryRepository.delete(child);
    }

    private void checkIsParentCategory(Category category)
    {
        if(category.getParent() != null)
        {
            throw new IllegalArgumentException("category " + category.getId() + " is not a parent category");
        }
    }

    @Override
    public void checkIsParentAndChildCategory(Category child, Category parent) {
        checkIsParentCategory(parent);
        if(!isChildCategory(child, parent))
        {
            throw new IllegalArgumentException("category " + parent.getId() + " is not parent of " + child.getId());
        }
    }

    @Override
    public Set<Category> getSubCategories(Long parentId) {
        Category parent = getCategoryById(parentId);
        checkIsParentCategory(parent);
        return parent.getChildCategories();
    }

    @Override
    public Category removeProductFromCategory(Category category, Product product) {
        category.getProducts().remove(product);
        return categoryRepository.save(category);
    }

}
