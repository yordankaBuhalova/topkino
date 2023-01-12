package fmi.softech.topkino.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", unique = true, nullable = false, length = 50)
    private String title;
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] img;
    @Column(name = "genre", nullable = false, length = 30)
    private String genre;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "description")
    private String description;
    @Column(name = "releaseYear", nullable = false, length = 4)
    private Integer releaseYear;
    @Column(name = "language", nullable = false, length = 30)
    private String language;
    @Column(name = "trailerUrl", length = 200)
    private String trailerUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img=" + Arrays.toString(img) +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", language='" + language + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return title.equals(movie.title) && genre.equals(movie.genre) && duration.equals(movie.duration) && description.equals(movie.description) && releaseYear.equals(movie.releaseYear) && language.equals(movie.language) && trailerUrl.equals(movie.trailerUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, duration, description, releaseYear, language, trailerUrl);
    }
}
