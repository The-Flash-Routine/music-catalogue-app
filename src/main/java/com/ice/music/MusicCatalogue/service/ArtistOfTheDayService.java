package com.ice.music.MusicCatalogue.service;

import com.ice.music.MusicCatalogue.entity.ArtistOfTheDay;
import com.ice.music.MusicCatalogue.exception.ArtistOfTheDayException;
import com.ice.music.MusicCatalogue.exception.code.ArtistOfTheDayExceptionCode;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistOfTheDayResponseDto;
import com.ice.music.MusicCatalogue.repository.ArtistOfTheDayRepository;
import com.ice.music.MusicCatalogue.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistOfTheDayService {

    private final ArtistOfTheDayRepository artistOfTheDayRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public void assignArtistOfTheDay() {
        LocalDate today = LocalDate.now();

        try {
            if (artistOfTheDayRepository.existsById(today)) {
                return;
            }

            Long maxArtistId = artistRepository.findMaxArtistId().orElse(-1L);
            if (maxArtistId == -1L) {
                throw new ArtistOfTheDayException(
                        "No Artist In Database",
                        ArtistOfTheDayExceptionCode.ARTIST_OF_THE_DAY_NO_ARTIST_IN_DATABASE
                );
            }

            Optional<ArtistOfTheDay> latestEntry = artistOfTheDayRepository.findLatestEntry();
            Long nextArtistId;

            if (latestEntry.isEmpty()) {
                // If there are no previous rows in Artist of The Day table, take row with highest ArtistId
                nextArtistId = maxArtistId;
            } else {
                Long previousId = latestEntry.get().getArtistId();
                nextArtistId = previousId + 1;
                if (nextArtistId > maxArtistId) {
                    nextArtistId = 0L;
                }
            }

            ArtistOfTheDay newArtistOfTheDay = new ArtistOfTheDay();
            newArtistOfTheDay.setDate(today);
            newArtistOfTheDay.setArtistId(nextArtistId);
            artistOfTheDayRepository.save(newArtistOfTheDay);

        } catch (ArtistOfTheDayException ex) {
            log.atError()
                    .setCause(ex)
                    .log("Unable to process assignArtistOfTheDay request");
        } catch (Exception ex) {
            ArtistOfTheDayException e = new ArtistOfTheDayException(
                    "Unable to fetch Artist of the day",
                    ArtistOfTheDayExceptionCode.ARTIST_OF_THE_DAY_DATABASE_EXCEPTION
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process assignArtistOfTheDay request");
        }
    }

    public ArtistOfTheDayResponseDto getArtistOfTheDay() {
        ArtistOfTheDayResponseDto artistOfTheDayResponseDto;
        try {
            /*
                If the Artist of the Day has not yet been updated yet by Spring scheduler:
                    Then intentionally returning the previous Artist of the Day instead of NULL.
                    This would only last for a few mins during 12AM midnight where the scheduler updates the table
                    That should be reasonable I guess
             */
            Optional<ArtistOfTheDay> optionalArtistOfTheDay = artistOfTheDayRepository.findLatestEntry();

            if (optionalArtistOfTheDay.isEmpty()) {
                throw new ArtistOfTheDayException(
                        "No Artist in Database",
                        ArtistOfTheDayExceptionCode.ARTIST_OF_THE_DAY_NO_ARTIST_IN_DATABASE
                );
            } else {
                artistOfTheDayResponseDto =
                        ArtistOfTheDayResponseDto
                                .builder()
                                .artistOfTheDayId(optionalArtistOfTheDay.get().getArtistId())
                                .date(optionalArtistOfTheDay.get().getDate())
                                .build();
            }
        }catch (ArtistOfTheDayException ex){
            log.atError()
                    .setCause(ex)
                    .log("Unable to process getArtistOfTheDay request");
            throw ex;
        }catch (Exception ex){
            ArtistOfTheDayException e = new ArtistOfTheDayException(
                    "Unable to fetch Artist of the day",
                    ArtistOfTheDayExceptionCode.ARTIST_OF_THE_DAY_DATABASE_EXCEPTION
            );
            log.atError()
                    .setCause(e)
                    .log("Unable to process getArtistOfTheDay request");
            throw e;
        }
        return artistOfTheDayResponseDto;
    }
}
