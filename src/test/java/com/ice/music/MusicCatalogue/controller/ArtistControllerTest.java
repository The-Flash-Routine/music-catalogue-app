package com.ice.music.MusicCatalogue.controller;

import com.ice.music.MusicCatalogue.model.request.dto.ArtistRequestDto;
import com.ice.music.MusicCatalogue.model.response.dto.ArtistResponseDto;
import com.ice.music.MusicCatalogue.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getArtist_ShouldReturnOk() throws Exception {
        Long artistId = 1L;
        ArtistResponseDto responseDto = ArtistResponseDto.builder()
                .artistId(artistId)
                .artistName("Test Artist")
                .artistLocation("London")
                .build();

        when(artistService.getArtist(artistId)).thenReturn(responseDto);

        mockMvc.perform(get("/artist/{artistId}", artistId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistId").value(artistId))
                .andExpect(jsonPath("$.artistName").value("Test Artist"))
                .andExpect(jsonPath("$.artistLocation").value("London"));
    }

    @Test
    public void createArtist_ShouldReturnCreated() throws Exception {
        ArtistRequestDto requestDto = new ArtistRequestDto();
        requestDto.setArtistName("New Artist");
        requestDto.setArtistLocation("Bristol");
        requestDto.setCreatedBy("admin");
        requestDto.setLastModifiedBy("admin");

        ArtistResponseDto responseDto = ArtistResponseDto.builder()
                .artistId(5L)
                .artistName("New Artist")
                .artistLocation("Bristol")
                .build();

        when(artistService.createArtist(any(ArtistRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/artist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.artistId").value(5L))
                .andExpect(jsonPath("$.artistName").value("New Artist"));
    }
}

