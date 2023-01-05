package com.CarRent.userService.repository;

import com.CarRent.userService.model.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRankRepository extends JpaRepository<UserRank,Long> {

    @Query(value = "SELECT * FROM userservicedb.user_rank s WHERE s.upper_bound >= ?1 AND s.lower_bound <= ?1", nativeQuery = true)
    Optional<UserRank> findRankForUser(Long day);
}
