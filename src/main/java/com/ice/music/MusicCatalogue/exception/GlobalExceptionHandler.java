package com.ice.music.MusicCatalogue.exception;

import com.ice.music.MusicCatalogue.exception.code.ArtistExceptionCode;
import com.ice.music.MusicCatalogue.exception.code.TrackExceptionCode;
import com.ice.music.MusicCatalogue.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArtistException.class)
    public ResponseEntity<ErrorResponseDto> handleArtistException(ArtistException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .code(ex.getExceptionCode() != null ? ex.getExceptionCode().toString() : ArtistExceptionCode.ARTIST_UNKNOWN.toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TrackException.class)
    public ResponseEntity<ErrorResponseDto> handleTrackException(TrackException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .code(ex.getExceptionCode() != null ? ex.getExceptionCode().toString() : TrackExceptionCode.TRACK_UNKNOWN.toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}