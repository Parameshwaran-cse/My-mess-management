package com.mess.management.repository;

import com.mess.management.entity.User;
import com.mess.management.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of user storage.
 *
 * ─── SWITCHING TO MYSQL ──────────────────────────────────────────────────────
 *  Delete this class and create:
 *
 *  @Repository
 *  public interface UserRepository extends JpaRepository<User, Long> {
 *      Optional<User> findByUsername(String username);
 *      Optional<User> findByEmail(String email);
 *      boolean existsByUsername(String username);
 *      boolean existsByEmail(String email);
 *      List<User> findByRole(Role role);
 *  }
 * ─────────────────────────────────────────────────────────────────────────────
 */
@Repository
public class UserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.getAndIncrement());
        }
        store.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByUsername(String username) {
        return store.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return store.values().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<User> findByRole(Role role) {
        return store.values().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public long count() {
        return store.size();
    }
}
