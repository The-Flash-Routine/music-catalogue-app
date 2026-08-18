package com.ice.music.MusicCatalogue.controller;

import com.ice.music.MusicCatalogue.model.request.dto.ArtistAliasRequestDto;
import com.ice.music.MusicCatalogue.model.request.dto.ArtistRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistAliasListResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistAliasResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistResponseDto;
import com.ice.music.MusicCatalogue.service.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/artist/{artistId}")
    private ResponseEntity<ArtistResponseDto> getArtist(
            @PathVariable(name = "artistId") Long artistId
    ){
        log.atInfo().log("Received request for getArtist for artistId: {}", artistId);
        ArtistResponseDto artistResponseDto = artistService.getArtist(artistId);
        log.atInfo().log("Successfully processed request for getArtist for artistId: {}", artistId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistResponseDto);
    }

    @PostMapping("/artist")
    private ResponseEntity<ArtistResponseDto> createArtist(
            @RequestBody ArtistRequestDto artistRequestDto
            ){
        log.atInfo().log("Received request for createArtist");
        ArtistResponseDto artistResponseDto = artistService.createArtist(artistRequestDto);
        log.atInfo().log("Successfully processed request for createArtist for artistId: {}", artistResponseDto.getArtistId());
        return ResponseEntity.status(HttpStatus.CREATED).body(artistResponseDto);
    }

    @PatchMapping("/artist/{artistId}")
    private ResponseEntity<ArtistResponseDto> patchArtist(
            @PathVariable(name = "artistId") Long artistId,
            @RequestBody ArtistRequestDto artistRequestDto
    ){
        log.atInfo().log("Received request for patchArtist for artistId: {}", artistId);
        ArtistResponseDto artistResponseDto = artistService.patchArtist(artistId, artistRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(artistResponseDto);
    }

    @PostMapping("/artist/{artistId}/alias")
    private ResponseEntity<ArtistAliasResponseDto> createArtistAlias(
            @PathVariable(name = "artistId") Long artistId,
            @RequestBody ArtistAliasRequestDto artistAliasRequestDto
            ){
        log.atInfo().log("Received request for createArtistAlias for artistId: {}", artistId);
        ArtistAliasResponseDto artistAliasResponseDto =
                artistService.createArtistAlias(artistId, artistAliasRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistAliasResponseDto);
    }

    @GetMapping("/artist/{artistId}/alias/{artistAliasId}")
    private ResponseEntity<ArtistAliasResponseDto> getArtistAlias(
            @PathVariable(name = "artistId") Long artistId,
            @PathVariable(name = "artistAliasId") Long artistAliasId
    ){
        log.atInfo().log("Received request for getArtistAlias for artistId: {} and artistAliasId: {}", artistId, artistAliasId);
        ArtistAliasResponseDto artistAliasResponseDto = artistService.getArtistAlias(artistId, artistAliasId);
        return ResponseEntity.status(HttpStatus.OK).body(artistAliasResponseDto);
    }

    @GetMapping("/artist/{artistId}/alias")
    private ResponseEntity<ArtistAliasListResponseDto> getArtistAliasList(
            @PathVariable(name = "artistId") Long artistId
    ){
        log.atInfo().log("Received request for getArtistAliasList for artistId: {}", artistId);
        ArtistAliasListResponseDto artistAliasListResponseDto = artistService.getArtistAliasList(artistId);
        return ResponseEntity.status(HttpStatus.OK).body(artistAliasListResponseDto);
    }

    @PatchMapping("/artist/{artistId}/alias/{artistAliasId}")
    private ResponseEntity<ArtistAliasResponseDto> patchArtistAlias(
            @PathVariable(name = "artistId") Long artistId,
            @PathVariable(name = "artistAliasId") Long artistAliasId,
            @RequestBody ArtistAliasRequestDto artistAliasRequestDto
    ){
        log.atInfo().log("Received request for patchArtistAlias for artistId: {} and artistAliasId: {}", artistId, artistAliasId);
        ArtistAliasResponseDto artistAliasResponseDto =
                artistService.patchArtistAlias(artistId, artistAliasId, artistAliasRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(artistAliasResponseDto);
    }
}
