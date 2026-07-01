package hu.anime.libary.AnimeLibary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "anime")
@Data  // ← Lombok: automatikus Getter/Setter/toString/equals/hashCode
@AllArgsConstructor
@NoArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Az anime neve kötelező!")
    @Size(min = 1, max = 255, message = "Az anime neve 1-255 karakter között lehet!")
    @Column(nullable = false, unique = true)
    private String animeName;

    @NotBlank(message = "A státusz kötelező!")
    @Pattern(regexp = "WATCHING|COMPLETED|DROPPED|PLANNED",
            message = "A státusz csak: WATCHING, COMPLETED, DROPPED vagy PLANNED lehet!")
    @Column(nullable = false)
    private String status;

    @Positive(message = "Az epizódszám pozitívnak kell lennie!")
    @Column(nullable = false)
    private int episodeNumber;

    @NotBlank(message = "A plot twist szöveg kötelező!")
    @Size(min = 5, max = 1000, message = "A plot twist 5-1000 karakter között lehet!")
    private String plotTwist;
}
