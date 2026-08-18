package com.ice.music.MusicCatalogue.service;

import com.ice.music.MusicCatalogue.entity.ArtistAlias;
import com.ice.music.MusicCatalogue.entity.Track;
import com.ice.music.MusicCatalogue.exception.ArtistException;
import com.ice.music.MusicCatalogue.exception.TrackException;
import com.ice.music.MusicCatalogue.exception.code.ArtistExceptionCode;
import com.ice.music.MusicCatalogue.exception.code.TrackExceptionCode;
import com.ice.music.MusicCatalogue.model.request.dto.TrackRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.TrackListResponseDto;
import com.ice.music.MusicCatalogue.model.response.dto.TrackResponseDto;
import com.ice.music.MusicCatalogue.repository.ArtistAliasRepository;
import com.ice.music.MusicCatalogue.repository.TrackRepository;
import com.ice.music.MusicCatalogue.service.helper.TrackHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

    private final TrackRepository trackRepository;

    private final ArtistAliasRepository artistAliasRepository;

    @Transactional
    public TrackResponseDto getTrack(Long trackId){
        Track foundTrack;
        try {
            Optional<Track> trackOpt = trackRepository.findById(trackId);
            if (trackOpt.isEmpty()) {
                TrackException e = new TrackException(
                        String.format("Track with Id: %s not found", trackId),
                        TrackExceptionCode.TRACK_NOT_FOUND
                );
                log.atError().setCause(e).log("Unable to process getTrack request for trackId: {}", trackId);
                throw e;
            }
            foundTrack = trackOpt.get();
        } catch (TrackException ex) {
            throw ex;
        } catch (Exception ex) {
            TrackException e = new TrackException(
                    "Unable to connect to Database",
                    TrackExceptionCode.TRACK_DATABASE_EXCEPTION,
                    ex
            );
            log.atError().setCause(e).log("Unable to process getTrack request for trackId: {}", trackId);
            throw e;
        }
        return TrackHelper.mapTrackToTrackResponseDto(foundTrack);
    }

    @Transactional
    public TrackListResponseDto getTrackList(Long artistAliasId, String trackGenre, String trackTitle, Integer page) {
        List<Track> tracks;
        try {
            ArtistAlias alias = artistAliasRepository.findById(artistAliasId)
                    .orElseThrow(() -> new ArtistException(
                            String.format("Artist alias with Id: %s not found", artistAliasId),
                            ArtistExceptionCode.ARTIST_ALIAS_NOT_FOUND
                    ));

            int pageNumber = (page != null && page > 0) ? page - 1 : 0;
            int pageSize = 10; // Or pass this as an argument/constant
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("trackId").descending());

            Page<Track> trackPage = trackRepository.findByAliasAndFilters(
                    artistAliasId,
                    trackGenre,
                    trackTitle,
                    pageable
            );

            List<TrackResponseDto> trackResponseDtos = trackPage.getContent().stream()
                    .map(TrackHelper::mapTrackToTrackResponseDto)
                    .collect(Collectors.toList());

            return TrackListResponseDto
                    .builder()
                    .artistAliasId(artistAliasId)
                    .trackList(trackResponseDtos)
                    .build();
        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process getTrackList request for artistAliasId: {}", artistAliasId);
            throw ex;
        } catch (Exception ex) {
            TrackException e = new TrackException(
                    "Unable to connect to Database",
                    TrackExceptionCode.TRACK_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process getTrackList request for artistAliasId: {}", artistAliasId);
            throw e;
        }
    }

    @Transactional
    public TrackResponseDto createTrack(TrackRequestDto trackRequestDto) {
        try {
            List<ArtistAlias> artistAliasList =
                    trackRequestDto
                            .getArtistAliasIdList()
                            .stream()
                            .map(ele ->
                                    artistAliasRepository.findById(ele)
                                        .orElseThrow(() -> new ArtistException(
                                                String.format("Artist alias with Id: %s not found", ele),
                                                ArtistExceptionCode.ARTIST_ALIAS_NOT_FOUND
                                        ))
                            )
                            .toList();



            long time = Instant.now().toEpochMilli();
            Track track = Track
                    .builder()
                    .trackTitle(trackRequestDto.getTrackTitle())
                    .trackGenre(trackRequestDto.getTrackGenre())
                    .trackLengthInSeconds(trackRequestDto.getTrackLengthInSeconds())
                    .trackS3Url(trackRequestDto.getTrackS3Url())
                    .artistAliasList(artistAliasList)
                    .createdAt(time)
                    .createdBy(trackRequestDto.getCreatedBy())
                    .lastModifiedAt(time)
                    .lastModifiedBy(trackRequestDto.getLastModifiedBy())
                    .build();

            Track savedTrack = trackRepository.save(track);

            return TrackHelper.mapTrackToTrackResponseDto(savedTrack);

        } catch (ArtistException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process createTrack request");
            throw ex;
        } catch (Exception ex) {
            TrackException e = new TrackException(
                    "Unable to connect to Database",
                    TrackExceptionCode.TRACK_DATABASE_EXCEPTION,
                    ex
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process create track request");
            throw e;
        }
    }
}
