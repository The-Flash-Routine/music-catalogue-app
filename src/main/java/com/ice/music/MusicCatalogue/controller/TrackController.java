package com.ice.music.MusicCatalogue.controller;


import com.ice.music.MusicCatalogue.constant.GenericConstants;
import com.ice.music.MusicCatalogue.model.request.dto.TrackRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.TrackListResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.TrackResponseDto;
import com.ice.music.MusicCatalogue.service.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/track/{trackId}")
    private ResponseEntity<TrackResponseDto> getTrack(
            @PathVariable(name = "trackId") Long trackId
    ){
        log.atInfo().log("Received request for getTrack for trackId: {}", trackId);
        TrackResponseDto trackResponseDto = trackService.getTrack(trackId);
        log.atInfo().log("Successfully processed request for getTrack for trackId: {}", trackId);
        return ResponseEntity.status(HttpStatus.OK).body(trackResponseDto);
    }

    @GetMapping("/search/track")
    private ResponseEntity<TrackListResponseDto> searchTrack(
            @RequestParam(name = "artistAliasId", required = true) Long artistAliasId,
            @RequestParam(name = "trackGenre", required = false, defaultValue = GenericConstants.ALL) String trackGenre,
            @RequestParam(name = "trackTitle", required = false, defaultValue = GenericConstants.ALL) String trackTitle,
            @RequestParam(name = "page", required = false, defaultValue = "1") String page
    ){
        log.atInfo().log("Received request for searchTrack for artistAliasId: {}", artistAliasId);
        TrackListResponseDto trackListResponseDto = trackService.getTrackList(artistAliasId, trackGenre, trackTitle, Integer.valueOf(page));
        log.atInfo().log("Successfully processed request for searchTrack for artistAliasId: {}", artistAliasId);
        return ResponseEntity.status(HttpStatus.OK).body(trackListResponseDto);
    }

    @PostMapping("/track")
    private ResponseEntity<TrackResponseDto> createTrack(
            @RequestBody TrackRequestDto trackRequestDto
    ){
        log.atInfo().log("Received request to create new track");
        TrackResponseDto trackResponseDto = trackService.createTrack(trackRequestDto);
        log.atInfo().log("Successfully processed request to create track and created trackId: {}", trackResponseDto.getTrackId());
        return ResponseEntity.status(HttpStatus.CREATED).body(trackResponseDto);
    }
}
