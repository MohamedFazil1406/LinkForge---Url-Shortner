package com.linkForge.app.service.impl;

import com.linkForge.app.model.Url;
import com.linkForge.app.repository.UrlRepository;
import com.linkForge.app.service.UrlService;
import com.linkForge.app.util.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Autowired
    private ShortCodeGenerator generator;

    @Override
    public String ShortUrl(Url url) {
        String code = generator.generateCode();

        url.setOriginalUrl(url.getOriginalUrl());
        url.setShortUrl(code);

        urlRepository.save(url);

        return code;

    }

    @Override
    public String findOriginalUrl(Url url) {

        System.out.println("Searching for: " + url.getShortUrl());

        Url existUrl = urlRepository.findByShortUrl(url.getShortUrl())
                .orElse(null);

        if (existUrl == null) {
            return null;
        }

        System.out.println("Original URL = " + existUrl.getOriginalUrl());

        return existUrl.getOriginalUrl();
    }
}
