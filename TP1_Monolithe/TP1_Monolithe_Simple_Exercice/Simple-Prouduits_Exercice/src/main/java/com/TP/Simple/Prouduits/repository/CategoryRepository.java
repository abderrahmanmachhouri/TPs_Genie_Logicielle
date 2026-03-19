package com.TP.Simple.Prouduits.repository;

import com.TP.Simple.Prouduits.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.List;
@RepositoryRestResource(path = "category")
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
