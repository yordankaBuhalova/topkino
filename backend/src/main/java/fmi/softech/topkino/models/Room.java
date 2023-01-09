package fmi.softech.topkino.models;

import java.util.Objects;

public class Room {
    private String name;
    private Integer seats;
    private RoomType type;
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
