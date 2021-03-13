package io.sen.canteenia.payload.request;

public class AddCartItemRequest {

    Long fooditem_id;

    int quantity;

    public AddCartItemRequest() {
    }

    public Long getFooditem_id() {
        return fooditem_id;
    }

    public void setFooditem_id(Long fooditem_id) {
        this.fooditem_id = fooditem_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public AddCartItemRequest(Long fooditem_id, int quantity) {
        this.fooditem_id = fooditem_id;
        this.quantity = quantity;
    }



}
