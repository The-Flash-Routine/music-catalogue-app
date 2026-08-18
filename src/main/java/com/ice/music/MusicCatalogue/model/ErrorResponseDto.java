package com.ice.music.MusicCatalogue.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponseDto {

    private String message;
    private String code;
}
