package com.mbh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ResourceUrlEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String shortUrl;
    private String strategy;

    public ResourceUrlEntity(String plainUrl, String urlEncoded , String strategy) {
        this.url = plainUrl;
        this.shortUrl = urlEncoded;
        this.strategy = strategy;
    }
}
