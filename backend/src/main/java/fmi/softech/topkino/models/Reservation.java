package fmi.softech.topkino.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "users", nullable = false)
    private Collection<User> users;

    @Column(name = "create_date", nullable = false, length = 4)
    private Timestamp createDate;

    @Column(name = "free_seats", nullable = false)
    private Integer freeSeats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "projection", nullable = false)
    private Projection projection;

    public Long getId() {
        return id;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", users=" + users +
                ", createDate=" + createDate +
                ", freeSeats=" + freeSeats +
                ", projection=" + projection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id) && users.equals(that.users) && createDate.equals(that.createDate) && freeSeats.equals(that.freeSeats) && projection.equals(that.projection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, users, createDate, freeSeats, projection);
    }
}
