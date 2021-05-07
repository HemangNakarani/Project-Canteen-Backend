package io.sen.canteenia.repository;

import io.micrometer.core.lang.NonNull;
import io.sen.canteenia.models.CartItem;
import io.sen.canteenia.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByUserid(Long id);

    boolean existsAllByUseridAndCartfooditem(Long uid, FoodItem fitem);

    void delete(@NonNull CartItem cartItem);

    void deleteByUserid(Long id);

}
