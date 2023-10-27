package com.mbh.encoder.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import com.mbh.encoder.UrlEncoder;
import com.mbh.entity.ResourceUrlEntity;
import com.mbh.exception.UrlNotFoundException;
import com.mbh.repository.ResourceUrlRepository;
import com.mbh.service.UrlShortenerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    private static final String COMPLETE_URL = "http://urlshortener.com";
    private static final String SHORT_URL = "Axgfls";
    private static final String STRATEGY = "base64";

    private UrlShortenerService target ;

    @Mock
    private ResourceUrlRepository repository ;

    @Mock
    private UrlEncoder encoder;

    @BeforeEach
    void setup(){
        target = new UrlShortenerService(repository , encoder);
    }
    @Test
    @DisplayName("Given a short url, when the decode operation is called, then the complete url is returned")
    void decodeUrlOk() throws UrlNotFoundException {
        ResourceUrlEntity entity = new ResourceUrlEntity(COMPLETE_URL,SHORT_URL,STRATEGY);
        when(repository.findByShortUrl(SHORT_URL)).thenReturn(Optional.of(entity));
        String result = target.decodeUrl(SHORT_URL);
        assertEquals(COMPLETE_URL , result);
    }

    @Test
    @DisplayName("Given an unknown short url, when the decode operation is called, then an Exception is thrown")
    void decodeUnknownUrl() {
        when(repository.findByShortUrl(SHORT_URL)).thenReturn(Optional.empty());
        Assertions.assertThrows(UrlNotFoundException.class, () -> {
            target.decodeUrl(SHORT_URL);
        });
    }

    @Test
    @DisplayName("Given a known url, when the encode operation is called, then the short URL is returned")
    void encodeUrlAlreadyPresent(){
        ResourceUrlEntity entity = new ResourceUrlEntity(COMPLETE_URL,SHORT_URL,STRATEGY);
        when(repository.findByUrl(COMPLETE_URL)).thenReturn(Optional.of(entity));
        String result = target.encodeUrl(COMPLETE_URL);
        assertEquals(SHORT_URL , result);
        verify(encoder , Mockito.never()).encode(COMPLETE_URL);
    }

    @Test
    @DisplayName("Given a new url, when the encode operation is called, then the short URL is returned")
    void encodeNewUrl(){
        when(repository.findByUrl(COMPLETE_URL)).thenReturn(Optional.empty());
        when(encoder.encode(COMPLETE_URL)).thenReturn(SHORT_URL);
        String result = target.encodeUrl(COMPLETE_URL);
        assertEquals(SHORT_URL , result);
        verify(encoder , Mockito.times(1)).encode(COMPLETE_URL);
    }
}
