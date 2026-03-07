// src/main/java/com/resqpot/resqpot/domain/content/entity/CurationContent.java
package com.resqpot.resqpot.domain.content.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "curation_contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private String contentType;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String linkUrl;

    private String relevanceDisaster;

    @Builder
    public CurationContent(String contentType, String title, String linkUrl, String relevanceDisaster) {
        this.contentType = contentType;
        this.title = title;
        this.linkUrl = linkUrl;
        this.relevanceDisaster = relevanceDisaster;
    }
}