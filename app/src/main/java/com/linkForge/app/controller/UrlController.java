package com.linkForge.app.controller;

import com.linkForge.app.model.Url;
import com.linkForge.app.model.User;
import com.linkForge.app.repository.UserRepository;
import com.linkForge.app.service.UrlService;
import com.linkForge.app.service.UserService;
import com.linkForge.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Url url) {

        String token = authHeader.substring(7);

        System.out.println("Incoming Original URL = " + url.getOriginalUrl());

        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        url.setUser(user);

        String shortUrl = urlService.ShortUrl(url);

        return ResponseEntity.ok(
                Map.of(
                        "shortUrl",
                        "http://localhost:8080/url/" + shortUrl
                )
        );
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrl) {

        Url url = new Url();
        url.setShortUrl(shortUrl);

        String originalUrl = urlService.findOriginalUrl(url);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originalUrl))
                .build();
    }
}
