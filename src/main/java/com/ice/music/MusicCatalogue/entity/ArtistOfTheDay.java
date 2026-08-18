package com.ice.music.MusicCatalogue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "artist_of_the_day")
public class ArtistOfTheDay {

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "artist_id")
    private Long artistId;
}
