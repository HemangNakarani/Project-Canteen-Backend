package io.sen.canteenia.models;

import javax.persistence.*;

@Entity
@Table(name = "orderitem")
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private Long canteenid;

    private String order_token;

    private int quantity = 0;

    private int amount = 0;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_fooditem", nullable = false)
    private FoodItem cartfooditem;

    public OrderedItem() {
    }

    public OrderedItem(Long id, Long userid, Long canteenid, String order_token, int quantity, int amount, String status, FoodItem cartfooditem) {
        this.id = id;
        this.userid = userid;
        this.canteenid = canteenid;
        this.order_token = order_token;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.cartfooditem = cartfooditem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCanteenid() {
        return canteenid;
    }

    public void setCanteenid(Long canteenid) {
        this.canteenid = canteenid;
    }

    public String getOrder_token() {
        return order_token;
    }

    public void setOrder_token(String order_token) {
        this.order_token = order_token;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FoodItem getCartfooditem() {
        return cartfooditem;
    }

    public void setCartfooditem(FoodItem cartfooditem) {
        this.cartfooditem = cartfooditem;
    }
}
