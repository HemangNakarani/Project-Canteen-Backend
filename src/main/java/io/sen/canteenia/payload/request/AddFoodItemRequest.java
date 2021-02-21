package io.sen.canteenia.payload.request;

import javax.validation.constraints.NotBlank;

public class AddFoodItemRequest {

    @NotBlank
    private String name;

    private String description = "";

    @NotBlank
    private int basePrise;

    public AddFoodItemRequest() {
    }

    public AddFoodItemRequest(@NotBlank String name, String description, @NotBlank int basePrise) {
        this.name = name;
        this.description = description;
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

    public int getBasePrise() {
        return basePrise;
    }

    public void setBasePrise(int basePrise) {
        this.basePrise = basePrise;
    }
}
