package io.sen.canteenia.controllers;

import io.sen.canteenia.models.Canteen;
import io.sen.canteenia.models.FoodItem;
import io.sen.canteenia.models.User;
import io.sen.canteenia.payload.request.AddFoodItemRequest;
import io.sen.canteenia.payload.request.UpdateFoodItemRequest;
import io.sen.canteenia.repository.CanteenRepository;
import io.sen.canteenia.repository.FoodItemRepository;
import io.sen.canteenia.repository.UserRepository;
import io.sen.canteenia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/api/manage/food")
public class FoodManageController {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    CanteenRepository canteenRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> getItems() {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Canteen canteen = getCanteen(userDetails);

        List<FoodItem> items = foodItemRepository.findByCanteen_id(canteen.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(items);
    }

    @PostMapping("/")
    public ResponseEntity<?> addItem(@Valid @RequestBody AddFoodItemRequest addFoodItemRequest) {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Canteen canteen = getCanteen(userDetails);

        FoodItem foodItem = foodItemRepository.save(new FoodItem(addFoodItemRequest.getName(),addFoodItemRequest.getDescription(),addFoodItemRequest.getBasePrise(),canteen.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(foodItem);
    }


    @PutMapping("/")
    public ResponseEntity<?> updateItem(@Valid @RequestBody UpdateFoodItemRequest updateFoodItemRequest) {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Canteen canteen = getCanteen(userDetails);
        FoodItem fd = foodItemRepository.findById(updateFoodItemRequest.getId()).orElseThrow(() -> new RuntimeException("Error: FoodItem is not found."));

        if(canteen.getId().equals(fd.getCanteen_id()))
        {
            fd.setDescription(updateFoodItemRequest.getDescription());
            fd.setBasePrise(updateFoodItemRequest.getBasePrise());
            foodItemRepository.save(fd);

            return ResponseEntity.ok(fd);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> setAvailibilityItem(@PathVariable("id") Long id,@Valid @RequestParam("available") Boolean available) {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Canteen canteen = getCanteen(userDetails);
        FoodItem fd = foodItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: FoodItem is not found."));

        if(canteen.getId().equals(fd.getCanteen_id()))
        {
            fd.setAvailable(available);
            foodItemRepository.save(fd);
            return ResponseEntity.ok(fd);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"FORBIDDEN\"}");
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteItem(@PathVariable("id") Long id) {
//
//        foodItemRepository.deleteById(id);
//
//        return ResponseEntity.ok("Deleted");
//    }

    private Canteen getCanteen(UserDetailsImpl userDetails)
    {
        User user = new User();
        user.setId(userDetails.getId());

        return canteenRepository.findByOwner(user).orElseThrow(() -> new RuntimeException("Error: Canteen is not found."));
    }

}