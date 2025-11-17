package hu.anime.libary.AnimeLibary.repository;

import hu.anime.libary.AnimeLibary.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    public List<Anime> findAllOrderByTitle();

}
