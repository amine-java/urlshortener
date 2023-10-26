package com.mbh.api;

import com.mbh.exception.UrlNotFoundException;
import com.mbh.service.UrlShortenerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlShortenerEndPoint {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    @ApiOperation("Shorten URL")
    public ResponseEntity<String> shortenUrl(@ApiParam(value = "url" , required = true) @RequestBody String url){
        // Retrieve the short URL
        String encodedUrl = urlShortenerService.encodeUrl(url);
        // Create location
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/retrieve/{encodedUrl}")
                .buildAndExpand(encodedUrl)
                .toUri();
        // Send Response
        return ResponseEntity.created(location).body(encodedUrl);
    }

    @GetMapping("/retrieve/{shortUrl}")
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
