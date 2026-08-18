package com.ice.music.MusicCatalogue.repository;

import com.ice.music.MusicCatalogue.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("SELECT t FROM Track t JOIN t.artistAliasList a " +
            "WHERE a.artistAliasId = :aliasId " +
            "AND (:genre = 'ALL' OR LOWER(t.trackGenre) = LOWER(:genre)) " +
            "AND (:title = 'ALL' OR LOWER(t.trackTitle) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Track> findByAliasAndFilters(
            @Param("aliasId") Long aliasId,
            @Param("genre") String genre,
            @Param("title") String title,
            Pageable pageable
    );
}
