package io.sen.canteenia.models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Fooditem")
public class FoodItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int basePrise;

    private boolean available = true;

    private String image_url;

    private String canteenname;

    private int stars = 0;

    private int number_of_rating = 0;

    @JoinColumn(name = "canteen_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Canteen.class, fetch = FetchType.LAZY)
    private Canteen canteen;

    @Column(name = "canteen_id")
    private Long canteen_id;

    public FoodItem() {
    }

    public FoodItem(String name, String canteenname, String description, int basePrise, Long canteen_id, String image_url) {
        this.name = name;
        this.description = description;
        this.canteenname = canteenname;
        this.basePrise = basePrise;
        this.canteen_id = canteen_id;
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCanteenname() {
        return canteenname;
    }

    public void setCanteenname(String canteenname) {
        this.canteenname = canteenname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBasePrise() {
        return basePrise;
    }

    public void setBasePrise(int basePrise) {
        this.basePrise = basePrise;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getNumber_of_rating() {
        return number_of_rating;
    }

    public void setNumber_of_rating(int number_of_rating) {
        this.number_of_rating = number_of_rating;
    }

    public Long getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(Long canteen_id) {
        this.canteen_id = canteen_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
