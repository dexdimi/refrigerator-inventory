package com.hylastix.fridgeservice.filter;

import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.FridgeItem;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FridgeSpecifications {

    public static Specification<FridgeItem> withFilters(String name, Category category){
        return(root, query, criteriaBuilder) -> {
          Predicate predicate = criteriaBuilder.conjunction();

          if(name != null && !name.trim().isEmpty()){
              predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                      criteriaBuilder.lower(root.get("name")),
                      "%" + name.toLowerCase() + "%"
              ));
          }

          if(category != null){
              predicate = criteriaBuilder.and(predicate,
                      criteriaBuilder.equal(root.get("category"), category));
          }

          return predicate;
        };
    }
}
