package com.mbh.repository;

import com.mbh.entity.ResourceUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceUrlRepository extends JpaRepository<ResourceUrlEntity , Long> {

    Optional<ResourceUrlEntity> findByShortUrl(String shortUrl);

    Optional<ResourceUrlEntity> findByUrl(String plainUrl);
}
