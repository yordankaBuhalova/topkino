package fmi.softech.topkino.models;

import java.util.Objects;

public class Movie {
    private String title;
    private String genre;
    private Integer duration;
    private String description;
    private Integer year;
    private String language;
    private String trailerUrl;

    public String getTitle() {
        return title;
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

    public Integer getYear() {
        return year;
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

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", language='" + language + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return title.equals(movie.title) && genre.equals(movie.genre) && duration.equals(movie.duration) && description.equals(movie.description) && year.equals(movie.year) && language.equals(movie.language) && trailerUrl.equals(movie.trailerUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, duration, description, year, language, trailerUrl);
    }
}
