package com.ice.music.MusicCatalogue.model.request.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TrackRequestDto {

    private List<Long> artistAliasIdList;
    private String trackTitle;
    private String trackGenre;
    private Integer trackLengthInSeconds;
    private String trackS3Url;
    private String createdBy;
    private String lastModifiedBy;
}
