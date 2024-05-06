package ru.mirea.MeatMarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.MeatMarket.entities.WebUser;

@Repository
public interface WebUserRepo extends JpaRepository<WebUser, Long> {
    WebUser getByUsername(String username);
}
