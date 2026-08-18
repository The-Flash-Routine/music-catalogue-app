package com.ice.music.MusicCatalogue.service;

import com.ice.music.MusicCatalogue.entity.Artist;
import com.ice.music.MusicCatalogue.exception.ArtistException;
import com.ice.music.MusicCatalogue.exception.code.ArtistExceptionCode;
import com.ice.music.MusicCatalogue.model.request.dto.ArtistRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistResponseDto;
import com.ice.music.MusicCatalogue.repository.ArtistAliasRepository;
import com.ice.music.MusicCatalogue.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private ArtistAliasRepository artistAliasRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    public void getArtist_Success() {
        Long artistId = 1L;
        Artist artist = Artist.builder()
                .artistId(artistId)
                .artistName("Test Artist")
                .artistLocation("London")
                .createdAt(System.currentTimeMillis())
                .createdBy("admin")
                .lastModifiedAt(System.currentTimeMillis())
                .lastModifiedBy("admin")
                .build();

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        ArtistResponseDto response = artistService.getArtist(artistId);

        assertNotNull(response);
        assertEquals(artistId, response.getArtistId());
        assertEquals("Test Artist", response.getArtistName());
        verify(artistRepository, times(1)).findById(artistId);
    }

    @Test
    public void getArtist_NotFound() {
        Long artistId = 99L;
        when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        ArtistException exception = assertThrows(ArtistException.class, () -> {
            artistService.getArtist(artistId);
        });

        assertEquals(ArtistExceptionCode.ARTIST_NOT_FOUND.toString(), exception.getExceptionCode());
        verify(artistRepository, times(1)).findById(artistId);
    }

    @Test
    public void createArtist_Success() {
        ArtistRequestDto requestDto = new ArtistRequestDto();
        requestDto.setArtistName("New Artist");
        requestDto.setArtistLocation("Manchester");
        requestDto.setCreatedBy("admin");
        requestDto.setLastModifiedBy("admin");

        Artist savedArtist = Artist.builder()
                .artistId(10L)
                .artistName("New Artist")
                .artistLocation("Manchester")
                .createdAt(System.currentTimeMillis())
                .createdBy("admin")
                .build();

        when(artistRepository.save(any(Artist.class))).thenReturn(savedArtist);

        ArtistResponseDto response = artistService.createArtist(requestDto);

        assertNotNull(response);
        assertEquals(10L, response.getArtistId());
        assertEquals("New Artist", response.getArtistName());
        verify(artistRepository, times(1)).save(any(Artist.class));
    }
}
