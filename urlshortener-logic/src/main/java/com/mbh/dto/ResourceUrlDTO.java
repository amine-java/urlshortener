package com.mbh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceUrlDTO {

    private String url;
    private String shortUrl;
    private Long nbClick;
    private LocalDate lastAccess;
}
