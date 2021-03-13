package io.sen.canteenia.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cartitem")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity = 0;

    private Long userid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_fooditem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FoodItem cartfooditem;

    public CartItem(int quantity, Long userid, FoodItem cartfooditem) {
        this.quantity = quantity;
        this.userid = userid;
        this.cartfooditem = cartfooditem;
    }

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public FoodItem getCartfooditem() {
        return cartfooditem;
    }

    public void setCartfooditem(FoodItem cartfooditem) {
        this.cartfooditem = cartfooditem;
    }
}
