package io.sen.canteenia.controllers;

import io.sen.canteenia.models.CartItem;
import io.sen.canteenia.models.ChatMessage;
import io.sen.canteenia.models.FoodItem;
import io.sen.canteenia.models.OrderedItem;
import io.sen.canteenia.payload.request.AddCartItemRequest;
import io.sen.canteenia.payload.response.*;
import io.sen.canteenia.repository.CartRepository;
import io.sen.canteenia.repository.FoodItemRepository;
import io.sen.canteenia.repository.OrderedItemRepository;
import io.sen.canteenia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    OrderedItemRepository orderedItemRepository;

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
        FoodItem foodItem = foodItemRepository.findById(addCartItemRequest.getFooditem_id()).orElseThrow(() -> new RuntimeException("Error: Can't Find This FoodItem"));

        cartItem.setUserid(userDetails.getId());
        cartItem.setQuantity(addCartItemRequest.getQuantity());
        cartItem.setCartfooditem(foodItem);

        if(cartRepository.existsAllByUseridAndCartfooditem(userDetails.getId(),foodItem))
        {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(alreadyExistsResponse);
        }

        CartItem newCartItem = cartRepository.save(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCartItem);
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/checkout/{uuid}")
    public ResponseEntity<?> checkOut(@PathVariable("uuid") String uuid) {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CartItem> listCartItems = cartRepository.findByUserid(userDetails.getId());

        if(listCartItems.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("Cart is Empty"));
        }

        ArrayList<OrderedItem> orderedItems = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();

        listCartItems.forEach((cartItem -> {

            OrderedItem orderedItem = new OrderedItem();

            orderedItem.setOrder_token(uuid);
            orderedItem.setUserid(userDetails.getId());
            orderedItem.setUsername(userDetails.getUsername());
            orderedItem.setUseremail(userDetails.getEmail());
            orderedItem.setCanteenid(cartItem.getCartfooditem().getCanteen_id());
            orderedItem.setCartfooditem(cartItem.getCartfooditem());
            orderedItem.setQuantity(cartItem.getQuantity());
            orderedItem.setPaid(true);

            hashSet.add("canteen-"+ cartItem.getCartfooditem().getCanteen_id().toString());

            int amount = cartItem.getQuantity()*cartItem.getCartfooditem().getBasePrise();

            orderedItem.setAmount(amount);
            orderedItem.setStatus("Pending");
            orderedItems.add(orderedItem);

        }));

        hashSet.forEach(id ->{
            messagingTemplate.convertAndSendToUser(
                    id,"/queue/messages",
                    new ChatMessage(userDetails.getId().toString(), userDetails.getUsername(), "NEW ORDER","NEW_ORDER"));
        });

        cartRepository.deleteAll(listCartItems);
        orderedItemRepository.saveAll(orderedItems);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderedItems);
    }

    @GetMapping("/myorders")
    public ResponseEntity<?> myOrders() {

        UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<OrderedItem> orderedItemList = orderedItemRepository.findAllByUserid(userDetails.getId());

        orderedItemList.removeIf( obj ->(obj.getStatus().equals("FullFilled")));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderedItemList);
    }

}
