package io.sen.canteenia.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderitem")
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private String username;

    private String useremail;

    private Long canteenid;

    private String order_token;

    private int quantity = 0;

    private int amount = 0;

    private boolean paid;

    private String status;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
    @Column(name = "updated_at", nullable = false)
    private Date undatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_fooditem", nullable = false)
    private FoodItem cartfooditem;

    public OrderedItem() {
    }

    public OrderedItem(Long id, Long userid, String username, String useremail, Long canteenid, String order_token, int quantity, int amount, String status, FoodItem cartfooditem) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.useremail = useremail;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUndatedAt() {
        return undatedAt;
    }

    public void setUndatedAt(Date undatedAt) {
        this.undatedAt = undatedAt;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
