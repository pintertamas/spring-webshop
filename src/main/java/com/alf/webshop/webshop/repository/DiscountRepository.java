package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findDiscountByCode(String code);
}
