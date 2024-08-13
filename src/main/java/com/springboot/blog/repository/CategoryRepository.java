package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
