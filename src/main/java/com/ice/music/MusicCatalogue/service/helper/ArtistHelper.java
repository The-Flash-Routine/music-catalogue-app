package com.ice.music.MusicCatalogue.service.helper;

import com.ice.music.MusicCatalogue.entity.Artist;
import com.ice.music.MusicCatalogue.entity.ArtistAlias;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistAliasResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistResponseDto;

public class ArtistHelper {

    public static ArtistResponseDto mapArtistToArtistResponseDto(Artist artist){

        return ArtistResponseDto
                .builder()
                .artistId(artist.getArtistId())
                .artistName(artist.getArtistName())
                .artistLocation(artist.getArtistLocation())
                .createdAt(artist.getCreatedAt())
                .createdBy(artist.getCreatedBy())
                .lastModifiedAt(artist.getLastModifiedAt())
                .lastModifiedBy(artist.getLastModifiedBy())
                .build();
    }

    public static ArtistAliasResponseDto mapArtistAliasToArtistAliasResponseDto(ArtistAlias artistAlias) {

        return ArtistAliasResponseDto
                .builder()
                .artistId(artistAlias.getArtist().getArtistId())
                .artistAliasId(artistAlias.getArtistAliasId())
                .artistAliasName(artistAlias.getArtistAliasName())
                .createdAt(artistAlias.getCreatedAt())
                .createdBy(artistAlias.getCreatedBy())
                .lastModifiedAt(artistAlias.getLastModifiedAt())
                .lastModifiedBy(artistAlias.getLastModifiedBy())
                .build();
    }
}
