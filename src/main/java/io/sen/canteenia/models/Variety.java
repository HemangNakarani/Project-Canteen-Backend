package io.sen.canteenia.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "variety")
public class Variety implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private int prise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fooditem_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FoodItem food_item;

    public Variety() {
    }

    public Variety(String type, int prise, FoodItem food_item) {
        this.type = type;
        this.prise = prise;
        this.food_item = food_item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    public FoodItem getFood_item() {
        return food_item;
    }

    public void setFood_item(FoodItem food_item) {
        this.food_item = food_item;
    }
}

