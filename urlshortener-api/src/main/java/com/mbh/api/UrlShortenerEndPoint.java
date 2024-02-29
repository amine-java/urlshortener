package com.mbh.api;

import com.mbh.dto.ResourceUrlDTO;
import com.mbh.exception.UrlNotFoundException;
import com.mbh.service.UrlShortenerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlShortenerEndPoint {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerEndPoint(UrlShortenerService urlShortenerService){
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping(value = "/shorten" , consumes = MediaType.TEXT_PLAIN_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Shorten URL")
    public ResponseEntity<ResourceUrlDTO> shortenUrl(@ApiParam(value = "url" , required = true) @RequestBody String url){
        // Retrieve the short URL
        ResourceUrlDTO resourceUrlDTO = urlShortenerService.encodeUrl(url);
        // Create location
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/retrieve/{encodedUrl}")
                .buildAndExpand(resourceUrlDTO.getShortUrl())
                .toUri();
        // Send Response
        return ResponseEntity.created(location).body(resourceUrlDTO);
    }

    @GetMapping(value = "/retrieve/{shortUrl}" , produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation("Retrieve original URL")
    public ResponseEntity<String> retrieveOriginalUrl(@ApiParam(value = "shortURL" , required = true) @PathVariable String shortUrl){
        try {
            String url = urlShortenerService.decodeUrl(shortUrl);
            return ResponseEntity.ok(url);
        } catch (UrlNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
