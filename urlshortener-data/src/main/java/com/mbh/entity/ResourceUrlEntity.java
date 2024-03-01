package com.mbh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceUrlEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String shortUrl;
    private String strategy;
    private Long nbClick = 0L;
    private LocalDate lastAccess;

    public void incrementNbClick(){
        this.nbClick++;
    }
}
