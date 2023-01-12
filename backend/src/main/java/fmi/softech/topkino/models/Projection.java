package fmi.softech.topkino.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "projections")
public class Projection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie")
    private Movie movie;
    @Column(name = "projection_on")
    private Timestamp projectionOn;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room")
    private Room room;

    @Column(name = "price", nullable = false)
    private Integer price;

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Timestamp getProjectionOn() {
        return projectionOn;
    }

    public Room getRoom() {
        return room;
    }

    public Integer getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setProjectionOn(Timestamp projectionOn) {
        this.projectionOn = projectionOn;
    }

    public void setRoom(Room room) {
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
        Projection that = (Projection) o;
        return id.equals(that.id) && movie.equals(that.movie) && projectionOn.equals(that.projectionOn) && room.equals(that.room) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, projectionOn, room, price);
    }
}
