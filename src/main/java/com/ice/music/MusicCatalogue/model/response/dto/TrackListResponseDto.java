package com.ice.music.MusicCatalogue.model.response.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrackListResponseDto {

    private Long artistAliasId;
    private List<TrackResponseDto> trackList;

}
