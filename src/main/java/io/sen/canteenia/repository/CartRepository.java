package io.sen.canteenia.repository;

import io.sen.canteenia.models.CartItem;
import io.sen.canteenia.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByUserid(Long id);

    boolean existsAllByUseridAndCartfooditem(Long uid, FoodItem fitem);

}
