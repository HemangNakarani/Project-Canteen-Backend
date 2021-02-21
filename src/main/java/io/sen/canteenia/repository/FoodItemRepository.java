package io.sen.canteenia.repository;

import io.sen.canteenia.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {

    List<FoodItem> findAllByAvailableEquals(Boolean bool);

    List<FoodItem> findByCanteen_id(@NonNull Long id);

}
