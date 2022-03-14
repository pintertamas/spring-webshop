package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
