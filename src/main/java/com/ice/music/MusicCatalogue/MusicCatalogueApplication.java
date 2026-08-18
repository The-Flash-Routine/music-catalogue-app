package com.ice.music.MusicCatalogue;

import com.ice.music.MusicCatalogue.constant.GenericConstants;
import com.ice.music.MusicCatalogue.entity.Artist;
import com.ice.music.MusicCatalogue.repository.ArtistRepository;
import com.ice.music.MusicCatalogue.service.ArtistOfTheDayService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class MusicCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicCatalogueApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ArtistRepository artistRepository, ArtistOfTheDayService artistOfTheDayService) {
		return args -> {
			long time = Instant.now().toEpochMilli();
			Artist artist = Artist.builder()
					.artistName("John Mayer")
					.artistLocation("Atlanta")
					.createdAt(time)
					.createdBy(GenericConstants.SYSTEM)
					.lastModifiedAt(time)
					.lastModifiedBy(GenericConstants.SYSTEM)
					.build();
			artistRepository.save(artist);
			artistOfTheDayService.assignArtistOfTheDay();
		};
	}

}
