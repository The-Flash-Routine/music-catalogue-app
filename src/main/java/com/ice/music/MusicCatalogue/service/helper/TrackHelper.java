package com.ice.music.MusicCatalogue.service.helper;

import com.ice.music.MusicCatalogue.entity.Track;
import com.ice.music.MusicCatalogue.model.response.dto.TrackResponseDto;

public class TrackHelper {

    public static TrackResponseDto mapTrackToTrackResponseDto(Track track){

        return TrackResponseDto
                .builder()
                .trackId(track.getTrackId())
                .trackTitle(track.getTrackTitle())
                .trackGenre(track.getTrackGenre())
                .trackLengthInSeconds(track.getTrackLengthInSeconds())
                .trackS3Url(track.getTrackS3Url())
                .createdAt(track.getCreatedAt())
                .createdBy(track.getCreatedBy())
                .lastModifiedAt(track.getLastModifiedAt())
                .lastModifiedBy(track.getLastModifiedBy())
                .build();
    }
}
