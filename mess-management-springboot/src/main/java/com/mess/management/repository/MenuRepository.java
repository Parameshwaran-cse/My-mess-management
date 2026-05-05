package com.mess.management.repository;

import com.mess.management.entity.Menu;
import com.mess.management.enums.MealType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of menu storage.
 *
 * ─── SWITCHING TO MYSQL ──────────────────────────────────────────────────────
 *  Delete this class and create:
 *
 *  @Repository
 *  public interface MenuRepository extends JpaRepository<Menu, Long> {
 *      List<Menu> findByDate(LocalDate date);
 *      List<Menu> findByMealType(MealType mealType);
 *      Optional<Menu> findByDateAndMealType(LocalDate date, MealType mealType);
 *  }
 * ─────────────────────────────────────────────────────────────────────────────
 */
@Repository
public class MenuRepository {

    private final Map<Long, Menu> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(idCounter.getAndIncrement());
        }
        store.put(menu.getId(), menu);
        return menu;
    }

    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Menu> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Menu> findByDate(LocalDate date) {
        return store.values().stream()
                .filter(m -> date.equals(m.getDate()))
                .collect(Collectors.toList());
    }

    public List<Menu> findByMealType(MealType mealType) {
        return store.values().stream()
                .filter(m -> m.getMealType() == mealType)
                .collect(Collectors.toList());
    }

    public Optional<Menu> findByDateAndMealType(LocalDate date, MealType mealType) {
        return store.values().stream()
                .filter(m -> date.equals(m.getDate()) && m.getMealType() == mealType)
                .findFirst();
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public long count() {
        return store.size();
    }
}
