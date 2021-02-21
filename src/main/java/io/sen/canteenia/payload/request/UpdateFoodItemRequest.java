package io.sen.canteenia.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdateFoodItemRequest {

    @NotBlank
    private Long id;

    private String description = "";

    @NotBlank
    private int basePrise;

    public UpdateFoodItemRequest() {
    }

    public UpdateFoodItemRequest(@NotBlank Long id, String description, @NotBlank int basePrise) {
        this.id = id;
        this.description = description;
        this.basePrise = basePrise;
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
}
