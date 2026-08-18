package com.ice.music.MusicCatalogue.controller;

import com.ice.music.MusicCatalogue.model.response.dto.ArtistOfTheDayResponseDto;
import com.ice.music.MusicCatalogue.service.ArtistOfTheDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtistOfTheDayController {

    private final ArtistOfTheDayService artistOfTheDayService;

    @GetMapping("/artist-of-the-day")
    public ResponseEntity<ArtistOfTheDayResponseDto> getArtistOfTheDay() {
        log.atInfo().log("Received request for getArtistOfTheDay");
        ArtistOfTheDayResponseDto artistOfTheDayResponseDto = artistOfTheDayService.getArtistOfTheDay();
        log.atInfo().log("Successfully processed request for getArtistOfTheDay");
        return ResponseEntity.status(HttpStatus.OK).body(artistOfTheDayResponseDto);
    }
}
