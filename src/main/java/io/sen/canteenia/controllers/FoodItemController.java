package io.sen.canteenia.controllers;
import io.sen.canteenia.models.Canteen;
import io.sen.canteenia.models.FoodItem;
import io.sen.canteenia.repository.CanteenRepository;
import io.sen.canteenia.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/food")
public class FoodItemController {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    CanteenRepository canteenRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllItems() {

        List<FoodItem> items = foodItemRepository.findAllByAvailableEquals(true);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> rateFoodItem(@PathVariable("id") Long id,@Valid @RequestParam("stars") int stars){

        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: FoodItem is not found."));

        foodItem.setStars(foodItem.getStars() + stars);
        foodItem.setNumber_of_rating(foodItem.getNumber_of_rating()+1);

        foodItemRepository.save(foodItem);

        return ResponseEntity.ok(foodItem);
    }

    @GetMapping("/canteens")
    public ResponseEntity<?> getAllCanteens(){

        List<Canteen> canteenList = canteenRepository.findAll();

        return ResponseEntity.ok(canteenList);
    }

    @GetMapping("/fromcanteen/{id}")
    public ResponseEntity<?> getFoodItemsFromCanteens(@PathVariable("id") Long id){

        List<FoodItem> foodItemList =  foodItemRepository.findByCanteen_id(id);

        return ResponseEntity.ok(foodItemList);
    }

}
