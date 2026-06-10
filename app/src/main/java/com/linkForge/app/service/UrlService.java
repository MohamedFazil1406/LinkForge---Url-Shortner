package com.linkForge.app.service;

import com.linkForge.app.model.Url;

public interface UrlService {

    String ShortUrl(Url url);

    String findOriginalUrl(Url url);
}
