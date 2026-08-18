package com.ice.music.MusicCatalogue.entity;


import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    private Long createdAt;
    private String createdBy;

    private Long lastModifiedAt;
    private String lastModifiedBy;
}
