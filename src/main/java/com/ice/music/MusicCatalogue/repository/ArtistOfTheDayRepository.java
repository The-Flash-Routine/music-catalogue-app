package com.ice.music.MusicCatalogue.repository;

import com.ice.music.MusicCatalogue.entity.ArtistOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ArtistOfTheDayRepository extends JpaRepository<ArtistOfTheDay, LocalDate> {
    @Query("SELECT a FROM ArtistOfTheDay a ORDER BY a.date DESC LIMIT 1")
    Optional<ArtistOfTheDay> findLatestEntry();
}
