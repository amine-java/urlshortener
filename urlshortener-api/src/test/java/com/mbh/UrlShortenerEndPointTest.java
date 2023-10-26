package com.mbh;

import com.mbh.entity.ResourceUrlEntity;
import com.mbh.repository.ResourceUrlRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlShortenerEndPointTest {

    private static final String ORIGINAL_URL = "http://urlshortener.com";

    private static final String SHORT_URL = "1XwzLB";

    private static final String API_RETRIEVE_URL_ENDPOINT = "/api/shorten/";

    private static final String API_SHORTEN_URL_ENDPOINT = "/api/shorten";
    @Autowired
    private ResourceUrlRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    void  setUp(){
        ResourceUrlEntity entity = new ResourceUrlEntity();
        entity.setUrl(ORIGINAL_URL);
        entity.setShortUrl(SHORT_URL);
        repository.save(entity);
    }

    @Test
    @DisplayName("Given an existing url stored, when the retrieve api is called, the original url is returned")
    void retrieveOriginalUrl() throws Exception {

        mockMvc
                .perform(get(API_RETRIEVE_URL_ENDPOINT + SHORT_URL))//
                .andDo(print())//
                .andExpect(status().isOk())//
                .andExpect(content().string(containsString(ORIGINAL_URL)));//
    }


    @Test
    @DisplayName("Given a url, when the shorten api is called, a short url is returned")
    void shortenOriginalUrl() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post(API_SHORTEN_URL_ENDPOINT ).content(ORIGINAL_URL))//
                .andDo(print())//
                .andExpect(status().isCreated())//
                .andExpect(content().string(containsString(SHORT_URL)));//
    }
}
