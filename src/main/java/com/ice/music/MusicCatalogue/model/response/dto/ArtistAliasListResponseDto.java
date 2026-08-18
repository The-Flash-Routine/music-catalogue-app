package com.ice.music.MusicCatalogue.model.response.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArtistAliasListResponseDto {

    private Long artistId;
    private List<ArtistAliasResponseDto> artistAliasList;
}
