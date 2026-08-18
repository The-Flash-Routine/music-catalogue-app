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
@Table(name = "artist")
@SuperBuilder
@NoArgsConstructor
public class Artist extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "artist_location")
    private String artistLocation;

    @OneToMany(mappedBy = "artist")
    private List<ArtistAlias> artistAliasList;

}
