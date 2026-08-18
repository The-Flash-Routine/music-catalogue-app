package com.ice.music.MusicCatalogue.model.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistResponseDto {

    private Long artistId;
    private String artistName;
    private String artistLocation;
    private Long createdAt;
    private String createdBy;
    private Long lastModifiedAt;
    private String lastModifiedBy;
}
