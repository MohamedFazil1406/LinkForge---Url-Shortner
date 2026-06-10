package com.linkForge.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String originalUrl;

    private String shortUrl;


}
