package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author yuanlb
 * @since 2022/9/28
 */
public interface UserRepository extends JpaRepository<UserEntity,Long>, QuerydslPredicateExecutor<UserEntity> {
}
