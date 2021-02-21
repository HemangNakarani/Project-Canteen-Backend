package io.sen.canteenia.models;
import com.sun.istack.Nullable;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "Canteen",uniqueConstraints = {
        @UniqueConstraint(columnNames = "owner"),
})

public class Canteen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String canteenName;

    public Canteen(Long id, String canteenName, String altName, boolean opened) {
        this.id = id;
        this.canteenName = canteenName;
        this.altName = altName;
        this.opened = opened;
    }

    @ColumnDefault("")
    private String altName;

    @ColumnDefault("true")
    boolean opened;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    public Canteen() {
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // customised for returning only id
    public Long getOwner() {
        return owner.getId();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}






