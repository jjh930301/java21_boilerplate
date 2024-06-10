package com.app.api.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.api.global.entities.UserEntity;

public interface UserJpaRepository extends  JpaRepository<UserEntity, Long> , UserRepository{
    
}
