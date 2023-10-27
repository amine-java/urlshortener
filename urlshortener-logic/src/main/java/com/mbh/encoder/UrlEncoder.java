package com.mbh.encoder;

public interface UrlEncoder {
    String encode(String url);

    String strategyName();
}
