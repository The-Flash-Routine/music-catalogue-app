package com.ice.music.MusicCatalogue.scheduler;

import com.ice.music.MusicCatalogue.service.ArtistOfTheDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtistOfTheDayScheduler {

    private final ArtistOfTheDayService artistOfTheDayService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleArtistOfTheDay() {
        artistOfTheDayService.assignArtistOfTheDay();
    }

}
