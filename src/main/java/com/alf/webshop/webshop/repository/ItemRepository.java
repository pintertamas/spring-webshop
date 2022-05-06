package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Category;
import com.alf.webshop.webshop.entity.Gender;
import com.alf.webshop.webshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ItemRepository extends JpaRepository<Item, String> {
    Item findItemById(Long id);

    List<Item> findAllByIdIn(List<Long> ids);

    List<Item> findAllByCategoryIn(List<Category> categories);

    List<Item> findAllByCategoryInAndGender(List<Category> categories, Gender gender);
}
