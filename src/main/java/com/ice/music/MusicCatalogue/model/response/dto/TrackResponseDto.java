package com.ice.music.MusicCatalogue.model.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackResponseDto {

    private Long trackId;
    private String trackTitle;
    private String trackGenre;
    private Integer trackLengthInSeconds;
    private String trackS3Url;
    private Long createdAt;
    private String createdBy;
    private Long lastModifiedAt;
    private String lastModifiedBy;

}
