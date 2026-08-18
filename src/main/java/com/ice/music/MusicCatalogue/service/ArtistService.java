package com.ice.music.MusicCatalogue.service;

import com.ice.music.MusicCatalogue.entity.Artist;
import com.ice.music.MusicCatalogue.entity.ArtistAlias;
import com.ice.music.MusicCatalogue.exception.ArtistException;
import com.ice.music.MusicCatalogue.exception.code.ArtistExceptionCode;
import com.ice.music.MusicCatalogue.model.request.dto.ArtistAliasRequestDto;
import com.ice.music.MusicCatalogue.model.request.dto.ArtistRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistAliasListResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistAliasResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistResponseDto;
import com.ice.music.MusicCatalogue.repository.ArtistAliasRepository;
import com.ice.music.MusicCatalogue.repository.ArtistRepository;
import com.ice.music.MusicCatalogue.service.helper.ArtistHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistAliasRepository artistAliasRepository;

    @Transactional
    public ArtistResponseDto getArtist(Long artistId) {
        Artist foundArtist;
        try{
            Optional<Artist> artist = artistRepository.findById(artistId);

            if(artist.isEmpty()){
                throw new ArtistException(
                        String.format("Artist with Id: %s not found", artistId),
                        ArtistExceptionCode.ARTIST_NOT_FOUND
                );
            }else{
                foundArtist = artist.get();
            }
        }catch (ArtistException ex){
            log.atError()
                    .setCause(ex)
                    .log("Unable to process getArtist request for artistId: {}", artistId);
            throw ex;
        }catch (Exception ex){
            ArtistException e = new ArtistException(
                    "Unable to connect to Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process getArtist request for artistId: {}", artistId);
            throw e;
        }

        return ArtistHelper.mapArtistToArtistResponseDto(foundArtist);
    }

    @Transactional
    public ArtistResponseDto patchArtist(Long artistId, ArtistRequestDto artistRequestDto) {
        Artist foundArtist;
        try {
            Optional<Artist> artistOpt = artistRepository.findById(artistId);
            if (artistOpt.isEmpty()) {
                throw new ArtistException(
                        String.format("Artist with Id: %s not found", artistId),
                        ArtistExceptionCode.ARTIST_NOT_FOUND
                );
            }
            foundArtist = artistOpt.get();

            if (artistRequestDto.getArtistName() != null) {
                foundArtist.setArtistName(artistRequestDto.getArtistName());
            }

            if (artistRequestDto.getArtistLocation() != null) {
                foundArtist.setArtistLocation(artistRequestDto.getArtistLocation());
            }

            foundArtist.setLastModifiedBy(artistRequestDto.getLastModifiedBy());

            long time = Instant.now().toEpochMilli();
            foundArtist.setLastModifiedAt(time);

            foundArtist = artistRepository.save(foundArtist);
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process patchArtist request for artistId: {}", artistId);
            throw ex;
        } catch (Exception ex) {
            ArtistException e = new ArtistException(
                    "Unable to update artist in Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process patchArtist request for artistId: {}", artistId);
            throw e;
        }
        return ArtistHelper.mapArtistToArtistResponseDto(foundArtist);
    }

    @Transactional
    public ArtistResponseDto createArtist(ArtistRequestDto artistRequestDto) {
        Artist savedArtist;
        try {
            long time = Instant.now().toEpochMilli();
            Artist artist = Artist
                    .builder()
                    .artistName(artistRequestDto.getArtistName())
                    .artistLocation(artistRequestDto.getArtistLocation())
                    .artistAliasList(new ArrayList<>())
                    .createdAt(time)
                    .createdBy(artistRequestDto.getCreatedBy())
                    .lastModifiedAt(time)
                    .lastModifiedBy(artistRequestDto.getLastModifiedBy())
                    .build();
            savedArtist = artistRepository.save(artist);
        } catch (Exception ex) {
            ArtistException e = new ArtistException(
                    "Unable to save artist to Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process createArtist request");
            throw e;
        }
        return ArtistHelper.mapArtistToArtistResponseDto(savedArtist);
    }

    @Transactional
    public ArtistAliasResponseDto getArtistAlias(Long artistId, Long artistAliasId) {
        ArtistAlias foundAlias;
        try {
            if (!artistRepository.existsById(artistId)) {
                throw new ArtistException(
                        String.format("Artist with Id: %s not found", artistId),
                        ArtistExceptionCode.ARTIST_NOT_FOUND
                );
            }
            Optional<ArtistAlias> aliasOpt = artistAliasRepository.findById(artistAliasId);
            if (aliasOpt.isEmpty()) {
                throw new ArtistException(
                        String.format("Artist alias with Id: %s not found", artistAliasId),
                        ArtistExceptionCode.ARTIST_ALIAS_NOT_FOUND
                );
            }
            foundAlias = aliasOpt.get();
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process getArtistAlias request for artistId: {}", artistId);
            throw ex;
        } catch (Exception ex) {
            ArtistException e = new ArtistException(
                    "Unable to connect to Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process getArtistAlias request for artistId: {}", artistId);
            throw e;
        }
        return ArtistHelper.mapArtistAliasToArtistAliasResponseDto(foundAlias);
    }

    @Transactional
    public ArtistAliasResponseDto createArtistAlias(Long artistId, ArtistAliasRequestDto artistAliasRequestDto) {
        ArtistAlias savedAlias;
        try {
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new ArtistException(
                            String.format("Artist with Id: %s not found", artistId),
                            ArtistExceptionCode.ARTIST_NOT_FOUND
                    ));
            long time = Instant.now().toEpochMilli();
            ArtistAlias alias = ArtistAlias
                    .builder()
                    .artistAliasName(artistAliasRequestDto.getArtistAliasName())
                    .artist(artist)
                    .createdAt(time)
                    .createdBy(artistAliasRequestDto.getCreatedBy())
                    .lastModifiedAt(time)
                    .lastModifiedBy(artistAliasRequestDto.getLastModifiedBy())
                    .build();

            savedAlias = artistAliasRepository.save(alias);
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process createArtistAlias request for artistId: {}", artistId);
            throw ex;
        } catch (Exception ex) {
            throw new ArtistException(
                    "Unable to save artist alias to Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
        }
        return ArtistHelper.mapArtistAliasToArtistAliasResponseDto(savedAlias);
    }

    @Transactional
    public ArtistAliasResponseDto patchArtistAlias(Long artistId, Long artistAliasId, ArtistAliasRequestDto artistAliasRequestDto) {
        ArtistAlias foundAlias;
        try {
            if (!artistRepository.existsById(artistId)) {
                throw new ArtistException(
                        String.format("Artist with Id: %s not found", artistId),
                        ArtistExceptionCode.ARTIST_NOT_FOUND
                );
            }
            Optional<ArtistAlias> aliasOpt = artistAliasRepository.findById(artistAliasId);
            if (aliasOpt.isEmpty()) {
                throw new ArtistException(
                        String.format("Artist alias with Id: %s not found", artistAliasId),
                        ArtistExceptionCode.ARTIST_ALIAS_NOT_FOUND
                );
            }
            foundAlias = aliasOpt.get();
            if (artistAliasRequestDto.getArtistAliasName() != null) {
                foundAlias.setArtistAliasName(artistAliasRequestDto.getArtistAliasName());
            }
            foundAlias = artistAliasRepository.save(foundAlias);
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process patchArtistAlias request for artistId: {}", artistId);
            throw ex;
        } catch (Exception ex) {
            throw new ArtistException(
                    "Unable to update artist alias in Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
        }
        return ArtistHelper.mapArtistAliasToArtistAliasResponseDto(foundAlias);
    }

    @Transactional
    public ArtistAliasListResponseDto getArtistAliasList(Long artistId) {
        Artist artist;
        try {
            artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new ArtistException(
                            String.format("Artist with Id: %s not found", artistId),
                            ArtistExceptionCode.ARTIST_NOT_FOUND
                    ));

            List<ArtistAliasResponseDto> aliasResponseDtos = artist.getArtistAliasList().stream()
                    .map(ArtistHelper::mapArtistAliasToArtistAliasResponseDto)
                    .collect(Collectors.toList());

            return ArtistAliasListResponseDto
                    .builder()
                    .artistId(artistId)
                    .artistAliasList(aliasResponseDtos)
                    .build();
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process getArtistAlias request for artistId: {}", artistId);
            throw ex;
        } catch (Exception ex) {
            ArtistException e = new ArtistException(
                    "Unable to connect to Database",
                    ArtistExceptionCode.ARTIST_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process getArtistAliasList request for artistId: {}", artistId);
            throw e;
        }
    }
}
