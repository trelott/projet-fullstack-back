package org.polytech.covid.center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Integer> {
    @Query("SELECT c FROM Center c where lower(c.city) LIKE lower(concat('%', :city, '%'))")
    public List<Center> findAllByCity(@Param("city") String city);
}
