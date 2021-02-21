package io.sen.canteenia.repository;

import io.sen.canteenia.models.Canteen;
import io.sen.canteenia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CanteenRepository extends JpaRepository<Canteen,Long> {

    Optional<Canteen> findByOwner(User user);

}
