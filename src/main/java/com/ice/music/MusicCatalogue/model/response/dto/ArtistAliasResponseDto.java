package com.ice.music.MusicCatalogue.model.response.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistAliasResponseDto {

    private Long artistId;
    private Long artistAliasId;
    private String artistAliasName;
    private Long createdAt;
    private String createdBy;
    private Long lastModifiedAt;
    private String lastModifiedBy;
}
