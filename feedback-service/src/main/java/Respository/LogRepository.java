package Respository;

import model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, UUID> {
    long countByCocktailId(String cocktailId);
}
