package io.sen.canteenia.repository;
import io.sen.canteenia.models.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem,Long> {

    List<OrderedItem> findAllByUserid(Long id);

    List<OrderedItem> findAllByStatusEquals(String status);

}

