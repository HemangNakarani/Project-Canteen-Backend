package io.sen.canteenia.controllers;
import io.sen.canteenia.models.FoodItem;
import io.sen.canteenia.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/food")
public class FoodItemController {

    @Autowired
    FoodItemRepository foodItemRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllItems() {

        List<FoodItem> items = foodItemRepository.findAllByAvailableEquals(true);

        return ResponseEntity.ok(items);
    }

}
