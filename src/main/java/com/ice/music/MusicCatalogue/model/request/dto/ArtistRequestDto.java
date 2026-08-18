package com.ice.music.MusicCatalogue.model.request.dto;

import lombok.Data;

@Data
public class ArtistRequestDto {

    private String artistName;
    private String artistLocation;
    private String createdBy;
    private String lastModifiedBy;
}
