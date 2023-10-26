package com.mbh.service;

import com.mbh.entity.SequenceIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Base64;
@Service
public class SimpleBase64Encoder implements UrlEncoder{

    private static final int MAX_LENGTH_URL = 10;

    @Autowired
    private EntityManager manager;

    /**
     * Encoding using a unique identifier along with a base64 encoding
     * @param url
     * @return encoded URL
     */
    @Override
    @Transactional
    public String encode(String url) {
        String concatUrlWithId = SequenceIdentifier.nextVal(manager) + url ;
        String base64encoded = Base64.getEncoder().encodeToString(concatUrlWithId.getBytes());
        return base64encoded.substring(0 , Math.min(MAX_LENGTH_URL, base64encoded.length()));
    }
}
