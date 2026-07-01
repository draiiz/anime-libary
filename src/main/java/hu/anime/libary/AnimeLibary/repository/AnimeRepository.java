package hu.anime.libary.AnimeLibary.repository;

import hu.anime.libary.AnimeLibary.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    // Anime keresése név alapján
    Optional<Anime> findByAnimeName(String animeName);

    // Összes anime rendezve név szerint
    List<Anime> findAllByOrderByAnimeName();

    // Anime keresése státusz alapján
    List<Anime> findByStatus(String status);

    // Anime keresése státusz alapján, rendezve név szerint
    List<Anime> findByStatusOrderByAnimeName(String status);

    // Státusz alapján szám
    @Query("SELECT COUNT(a) FROM Anime a WHERE a.status = :status")
    long countByStatus(@Param("status") String status);

    // Anime létezik-e név alapján?
    boolean existsByAnimeName(String animeName);

    // Összes manga típusút get
    @Query("SELECT a FROM Anime a WHERE a.status IN ('WATCHING', 'COMPLETED', 'PLANNED') ORDER BY a.animeName")
    List<Anime> findAllActiveAnimes();

    // Keresés név szerint (Like pattern)
    @Query("SELECT a FROM Anime a WHERE LOWER(a.animeName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY a.animeName")
    List<Anime> searchByName(@Param("searchTerm") String searchTerm);
}