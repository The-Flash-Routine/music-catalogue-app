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
@Table(name = "artist_alias")
@SuperBuilder
@NoArgsConstructor
public class ArtistAlias extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_alias_id")
    private Long artistAliasId;

    @Column(name = "artist_alias_name", nullable = false)
    private String artistAliasName;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "artistAliasList")
    private List<Track> trackList;
}
