package com.ice.music.MusicCatalogue.model.response.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ArtistOfTheDayResponseDto {

    private LocalDate date;
    private Long artistOfTheDayId;
}
