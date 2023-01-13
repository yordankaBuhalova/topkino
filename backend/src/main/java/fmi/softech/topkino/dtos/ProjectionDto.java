package fmi.softech.topkino.dtos;

import java.sql.Timestamp;
import java.util.Objects;

public class ProjectionDto {
    private Long id;
    private Long movie;
    private Timestamp projectionOn;
    private Long room;
    private Integer price;

    public Long getId() {
        return id;
    }

    public Long getMovie() {
        return movie;
    }

    public Timestamp getProjectionOn() {
        return projectionOn;
    }

    public Long getRoom() {
        return room;
    }

    public Integer getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Long movie) {
        this.movie = movie;
    }

    public void setProjectionOn(Timestamp projectionOn) {
        this.projectionOn = projectionOn;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Projection{" +
                "id=" + id +
                ", movie=" + movie +
                ", projectionOn=" + projectionOn +
                ", room=" + room +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectionDto that = (ProjectionDto) o;
        return id.equals(that.id) && movie.equals(that.movie) && projectionOn.equals(that.projectionOn) && room.equals(that.room) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, projectionOn, room, price);
    }
}
