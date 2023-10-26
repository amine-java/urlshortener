package com.mbh.service;

import com.mbh.entity.ResourceUrlEntity;
import com.mbh.exception.UrlNotFoundException;
import com.mbh.repository.ResourceUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {

    @Autowired
    private ResourceUrlRepository repository;

    @Autowired
    private UrlEncoder urlEncoder;

    /**
     * Method that retrieves the original URL from the short url, if no URL exists in the DB, then an Exception is thrown.
     * @param shortUrl
     * @return original URL
     * @throws UrlNotFoundException
     */
    public String decodeUrl(String shortUrl) throws UrlNotFoundException {
        return repository//
                .findByShortUrl(shortUrl)//
                .map(ResourceUrlEntity::getUrl)//
                .orElseThrow(UrlNotFoundException::new);//
    }

    /**
     * Method that takes a plain url and shortens it through encoding
     * @param plainUrl
     * @return encoded URL
     */
    public String encodeUrl(String plainUrl){
        Optional<ResourceUrlEntity> url = repository.findByUrl(plainUrl);
        if (url.isPresent()){
            return url.get().getShortUrl();
        }
        String urlEncoded = urlEncoder.encode(plainUrl);
        repository.save(new ResourceUrlEntity(plainUrl , urlEncoded));
        return urlEncoded;
    }

}
