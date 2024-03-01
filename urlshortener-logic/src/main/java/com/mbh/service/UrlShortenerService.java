package com.mbh.service;

import com.mbh.dto.ResourceUrlDTO;
import com.mbh.entity.ResourceUrlEntity;
import com.mbh.exception.UrlNotFoundException;
import com.mbh.mapper.ResourceUrlMapper;
import com.mbh.repository.ResourceUrlRepository;
import com.mbh.encoder.UrlEncoder;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final ResourceUrlRepository repository;

    private final UrlEncoder urlEncoder;

    private final ResourceUrlMapper mapper = Mappers.getMapper(ResourceUrlMapper.class);

    @Autowired
    public UrlShortenerService(ResourceUrlRepository repository, UrlEncoder urlEncoder) {
        this.repository = repository;
        this.urlEncoder = urlEncoder;
    }

    /**
     * Method that retrieves the original URL from the short url, if no URL exists in the DB, then an Exception is thrown.
     * @param shortUrl
     * @return original URL
     * @throws UrlNotFoundException
     */
    public String decodeUrl(String shortUrl) throws UrlNotFoundException {
        // Fetch original URL
        var resourceUrlEntity = repository//
                .findByShortUrl(shortUrl)//
                .orElseThrow(UrlNotFoundException::new);//

        // Update statistics
        resourceUrlEntity.incrementNbClick();
        resourceUrlEntity.setLastAccess(LocalDate.now());
        repository.save(resourceUrlEntity);

        return resourceUrlEntity.getUrl();
    }

    /**
     * Method that takes a plain url and shortens it through encoding
     * @param plainUrl
     * @return encoded URL
     */
    public ResourceUrlDTO encodeUrl(String plainUrl){
        Optional<ResourceUrlEntity> url = repository.findByUrl(plainUrl);
        if (url.isPresent()){
            return mapper.convert(url.get());
        }
        String urlEncoded = urlEncoder.encode(plainUrl);

        var entity = ResourceUrlEntity.builder()//
                .url(plainUrl)//
                .shortUrl(urlEncoded)//
                .strategy(urlEncoder.strategyName())//
                .nbClick(0L)
                .lastAccess(null)
                .build();

        repository.save(entity);
        return mapper.convert(entity);
    }

}
