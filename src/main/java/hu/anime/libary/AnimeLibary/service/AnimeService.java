package hu.anime.libary.AnimeLibary.service;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.exception.AnimeException;
import hu.anime.libary.AnimeLibary.repository.AnimeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j  // ← Logging automatikusan
public class AnimeService {

    private final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Transactional  // ← DATABASE TRANSACTION
    public Anime addAnime(Anime anime) {
        log.info("Anime hozzáadása: {}", anime.getAnimeName());

        if (animeRepository.existsByAnimeName(anime.getAnimeName())) {
            log.warn("Anime már létezik: {}", anime.getAnimeName());
            throw new AnimeException("Az anime már létezik: " + anime.getAnimeName());
        }

        Anime saved = animeRepository.save(anime);
        log.info("Anime sikeresen mentve. ID: {}", saved.getId());
        return saved;
    }

    public List<Anime> getAllAnime() {
        log.debug("Összes anime lekérése");
        return animeRepository.findAll();
    }

    public List<Anime> getAllSortedByName() {
        log.debug("Összes anime lekérése, rendezve név szerint");
        return animeRepository.findAllByOrderByAnimeName();
    }

    public Optional<Anime> getAnimeById(Long id) {
        log.debug("Anime keresése ID-vel: {}", id);

        if (id == null || id <= 0) {
            log.warn("Érvénytelen ID: {}", id);
            throw new AnimeException("Az ID pozitívnak kell lennie!");
        }

        return animeRepository.findById(id);
    }

    @Transactional
    public void deleteAnime(Long id) {
        log.info("Anime törlése. ID: {}", id);

        if (!animeRepository.existsById(id)) {
            log.error("Anime nem található törléshez. ID: {}", id);
            throw new AnimeException("Anime nem található az ID-vel: " + id);
        }

        animeRepository.deleteById(id);
        log.info("Anime sikeresen törölve. ID: {}", id);
    }

    @Transactional
    public Anime updateAnime(Long id, Anime anime) {
        log.info("Anime frissítése. ID: {}", id);

        return animeRepository.findById(id).map(existingAnime -> {
            if (!existingAnime.getAnimeName().equals(anime.getAnimeName()) &&
                    animeRepository.existsByAnimeName(anime.getAnimeName())) {
                log.warn("Az anime név már létezik: {}", anime.getAnimeName());
                throw new AnimeException("Az anime már létezik: " + anime.getAnimeName());
            }

            existingAnime.setAnimeName(anime.getAnimeName());
            existingAnime.setStatus(anime.getStatus());
            existingAnime.setEpisodeNumber(anime.getEpisodeNumber());
            existingAnime.setPlotTwist(anime.getPlotTwist());

            Anime updated = animeRepository.save(existingAnime);
            log.info("Anime sikeresen frissítve. ID: {}", id);
            return updated;
        }).orElseThrow(() -> {
            log.error("Anime nem található. ID: {}", id);
            return new AnimeException("Anime nem található az ID-vel: " + id);
        });
    }

    public long countByStatus(String status) {
        log.debug("Anime darabszám státusz alapján: {}", status);
        validateStatus(status);
        return animeRepository.countByStatus(status);
    }

    public Optional<Anime> getAnimeByName(String animeName) {
        log.debug("Anime keresése név alapján: {}", animeName);
        if (animeName == null || animeName.trim().isEmpty()) {
            throw new AnimeException("Az anime néve nem lehet üres!");
        }
        return animeRepository.findByAnimeName(animeName);
    }

    public List<Anime> searchAnime(String searchTerm) {
        log.debug("Anime keresése kifejezéssel: {}", searchTerm);
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new AnimeException("A keresési kifejezés nem lehet üres!");
        }
        return animeRepository.searchByName(searchTerm.trim());
    }

    private void validateStatus(String status) {
        if (status == null || !status.matches("WATCHING|COMPLETED|DROPPED|PLANNED")) {
            throw new AnimeException("Érvénytelen státusz: " + status);
        }
    }
}