package com.mess.management.repository;

import com.mess.management.entity.Feedback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of feedback storage.
 *
 * ─── SWITCHING TO MYSQL ──────────────────────────────────────────────────────
 *  Delete this class and create:
 *
 *  @Repository
 *  public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
 *      List<Feedback> findByUserId(Long userId);
 *  }
 * ─────────────────────────────────────────────────────────────────────────────
 */
@Repository
public class FeedbackRepository {

    private final Map<Long, Feedback> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Feedback save(Feedback feedback) {
        if (feedback.getId() == null) {
            feedback.setId(idCounter.getAndIncrement());
        }
        store.put(feedback.getId(), feedback);
        return feedback;
    }

    public Optional<Feedback> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Feedback> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Feedback> findByUserId(Long userId) {
        return store.values().stream()
                .filter(f -> f.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public long count() {
        return store.size();
    }
}
