package com.ice.music.MusicCatalogue.repository;

import com.ice.music.MusicCatalogue.entity.ArtistAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistAliasRepository extends JpaRepository<ArtistAlias, Long> {
}
