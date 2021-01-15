package com.cleverit.springboottest.core.repository;

import com.cleverit.springboottest.core.entity.Sprite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpriteInterfaceRepository extends JpaRepository<Sprite, Long> {
}
