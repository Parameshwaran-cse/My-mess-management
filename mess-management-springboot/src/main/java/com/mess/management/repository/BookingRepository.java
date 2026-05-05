package com.mess.management.repository;

import com.mess.management.entity.Booking;
import com.mess.management.enums.BookingStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of booking storage.
 *
 * ─── SWITCHING TO MYSQL ──────────────────────────────────────────────────────
 *  Delete this class and create:
 *
 *  @Repository
 *  public interface BookingRepository extends JpaRepository<Booking, Long> {
 *      List<Booking> findByUserId(Long userId);
 *      List<Booking> findByStatus(BookingStatus status);
 *      long countByStatus(BookingStatus status);
 *  }
 * ─────────────────────────────────────────────────────────────────────────────
 */
@Repository
public class BookingRepository {

    private final Map<Long, Booking> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(idCounter.getAndIncrement());
        }
        store.put(booking.getId(), booking);
        return booking;
    }

    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Booking> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Booking> findByUserId(Long userId) {
        return store.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Booking> findByStatus(BookingStatus status) {
        return store.values().stream()
                .filter(b -> b.getStatus() == status)
                .collect(Collectors.toList());
    }

    public long countByStatus(BookingStatus status) {
        return store.values().stream()
                .filter(b -> b.getStatus() == status)
                .count();
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
