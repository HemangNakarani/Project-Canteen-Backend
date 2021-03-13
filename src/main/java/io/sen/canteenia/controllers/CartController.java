package io.sen.canteenia.controllers;

import io.sen.canteenia.models.CartItem;
import io.sen.canteenia.models.FoodItem;
import io.sen.canteenia.payload.request.AddCartItemRequest;
import io.sen.canteenia.payload.response.*;
import io.sen.canteenia.repository.CartRepository;
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
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CreatedResponse createdResponse;

    @Autowired
    ForbiddenResponse forbiddenResponse;

    @Autowired
    UpdatedResponse updatedResponse;

    @Autowired
    DeletedResponse deletedResponse;

    @Autowired
    AlreadyExistsResponse alreadyExistsResponse;

    @PostMapping("/")
    public ResponseEntity<?> addCartItem(@Valid @RequestBody AddCartItemRequest addCartItemRequest)
    {
        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CartItem cartItem = new CartItem();
        FoodItem foodItem = new FoodItem();

        cartItem.setUserid(userDetails.getId());
        cartItem.setQuantity(addCartItemRequest.getQuantity());
        foodItem.setId(addCartItemRequest.getFooditem_id());
        cartItem.setCartfooditem(foodItem);

        if(cartRepository.existsAllByUseridAndCartfooditem(userDetails.getId(),foodItem))
        {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(alreadyExistsResponse);
        }

        cartRepository.save(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCartItems()
    {
        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CartItem> listCartItems = cartRepository.findByUserid(userDetails.getId());

        return ResponseEntity.ok(listCartItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long id)
    {
        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CartItem cartItem = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Can't Find cartItem"));

        if(!cartItem.getUserid().equals(userDetails.getId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);

        cartRepository.deleteById(id);

        return ResponseEntity.ok(deletedResponse);

    }

    @PutMapping("/{id}/inc")
    public ResponseEntity<?> increaseCartItem(@PathVariable("id") Long id)
    {
        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CartItem cartItem = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Can't Find cartItem"));

        if(!cartItem.getUserid().equals(userDetails.getId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);

        cartItem.setQuantity(cartItem.getQuantity()+1);

        cartRepository.save(cartItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedResponse);
    }

    @PutMapping("/{id}/dec")
    public ResponseEntity<?> decreaseCartItem(@PathVariable("id") Long id)
    {
        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CartItem cartItem = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Can't Find cartItem"));

        if(!cartItem.getUserid().equals(userDetails.getId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);

        if(cartItem.getQuantity() == 1)
        {
            cartRepository.deleteById(id);
            return ResponseEntity.ok(deletedResponse);
        }

        cartItem.setQuantity(cartItem.getQuantity()-1);

        cartRepository.save(cartItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedResponse);
    }

}
