package com.ice.music.MusicCatalogue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "track")
@SuperBuilder
@NoArgsConstructor
public class Track extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long trackId;

    @Column(name = "track_title", nullable = false)
    private String trackTitle;

    @Column(name = "track_genre")
    private String trackGenre;

    @Column(name = "track_length")
    private Integer trackLengthInSeconds;

    @Column(name = "track_url", nullable = false)
    private String trackS3Url;

    @ManyToMany
    @JoinTable(
            name = "artist_alias_track",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artisr_alias_id")
    )
    private List<ArtistAlias> artistAliasList;
}
