// src/main/java/com/resqpot/resqpot/domain/content/entity/GuideContentMapping.java
package com.resqpot.resqpot.domain.content.entity;

import com.resqpot.resqpot.domain.disaster.entity.ActionGuide;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guide_contents_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideContentMapping {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private ActionGuide actionGuide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private CurationContent curationContent;

    @Builder
    public GuideContentMapping(ActionGuide actionGuide, CurationContent curationContent) {
        this.actionGuide = actionGuide;
        this.curationContent = curationContent;
    }
}