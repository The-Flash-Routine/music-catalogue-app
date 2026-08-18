package com.ice.music.MusicCatalogue.repository;

import com.ice.music.MusicCatalogue.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT MAX(a.artistId) FROM Artist a")
    Optional<Long> findMaxArtistId();

}
