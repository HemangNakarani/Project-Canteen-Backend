package io.sen.canteenia.payload.request;

import javax.validation.constraints.NotBlank;

public class AddFoodItemRequest {

    @NotBlank
    private String name;

    private String description = "";

    private String image_url;

    @NotBlank
    private int basePrise;

    public AddFoodItemRequest() {
    }

    public AddFoodItemRequest(@NotBlank String name, String description, String image_url, @NotBlank int basePrise) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.basePrise = basePrise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getBasePrise() {
        return basePrise;
    }

    public void setBasePrise(int basePrise) {
        this.basePrise = basePrise;
    }
}
