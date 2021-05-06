package io.sen.canteenia.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdateFoodItemRequest {

    @NotBlank
    private Long id;

    private String description = "";

    @NotBlank
    private int basePrise;

    private  String image_url;

    public UpdateFoodItemRequest() {
    }

    public UpdateFoodItemRequest(Long id, String description, int basePrise, String image_url) {
        this.id = id;
        this.description = description;
        this.basePrise = basePrise;
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
