package gr.hua.dit.ds.ds_lab_2024.repositories;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByCityInAndAvailableAndTypeIn(List<String> cities, Boolean available, List<String> type);

    @Query("SELECT DISTINCT p.city FROM Property p")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT p.type FROM Property p")
    List<String> findDistinctPropertyTypes();

    List<Property> findByCityInAndTypeIn(List<String> cities, List<String> type);
}