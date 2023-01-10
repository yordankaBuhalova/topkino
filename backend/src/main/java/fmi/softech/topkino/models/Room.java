package fmi.softech.topkino.models;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "type")
    private RoomType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private enum RoomType {
        R_2D,
        R_3D;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public RoomType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", seats=" + seats +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name) && seats.equals(room.seats) && type == room.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, seats, type);
    }
}
