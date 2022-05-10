package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findDiscountByCode(String code);
}
