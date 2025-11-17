package hu.anime.libary.AnimeLibary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Anime {

    @Id
    @GeneratedValue
    private Long id;

    private String animeName;
    private String status;
    private int episodeNumber;
    private String plotTwist;

    public Anime(Long id, String animeName, String status, String plotTwist, int episodeNumber) {
        this.id = id;
        this.animeName = animeName;
        this.status = status;
        this.plotTwist = plotTwist;
        this.episodeNumber = episodeNumber;
    }

    public Anime() {
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return animeName;
    }

    public void setTitle(String animeName) {
        this.animeName = animeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlotTwist() {
        return plotTwist;
    }

    public void setPlotTwist(String plotTwist) {
        this.plotTwist = plotTwist;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}
