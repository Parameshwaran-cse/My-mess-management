package com.mess.management.service;

import com.mess.management.dto.request.MenuRequest;
import com.mess.management.dto.response.MenuResponse;
import com.mess.management.entity.Menu;
import com.mess.management.entity.MenuItem;
import com.mess.management.enums.MealType;
import com.mess.management.exception.BadRequestException;
import com.mess.management.exception.ResourceNotFoundException;
import com.mess.management.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for mess menu management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    // ─── Get All Menus ───────────────────────────────────────────────────────

    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::from)
                .toList();
    }

    // ─── Get by ID ───────────────────────────────────────────────────────────

    public MenuResponse getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", id));
        return MenuResponse.from(menu);
    }

    // ─── Get Today's Menus ───────────────────────────────────────────────────

    public List<MenuResponse> getTodaysMenus() {
        return menuRepository.findByDate(LocalDate.now()).stream()
                .map(MenuResponse::from)
                .toList();
    }

    // ─── Create Menu ─────────────────────────────────────────────────────────

    public MenuResponse createMenu(MenuRequest request) {
        MealType mealType = parseMealType(request.getMealType());

        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();

        // Prevent duplicate meal type for the same date
        menuRepository.findByDateAndMealType(date, mealType).ifPresent(existing -> {
            throw new BadRequestException(
                    mealType + " menu already exists for date " + date + ". Use PUT to update it.");
        });

        List<MenuItem> items = buildMenuItems(request);

        Menu menu = Menu.builder()
                .mealType(mealType)
                .items(items)
                .alternates(request.getAlternates() != null ? request.getAlternates() : new ArrayList<>())
                .date(date)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Menu saved = menuRepository.save(menu);
        log.info("Menu created: {} for {}", mealType, date);
        return MenuResponse.from(saved);
    }

    // ─── Update Menu ─────────────────────────────────────────────────────────

    public MenuResponse updateMenu(Long id, MenuRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", id));

        if (request.getMealType() != null) {
            menu.setMealType(parseMealType(request.getMealType()));
        }
        if (request.getItems() != null) {
            menu.setItems(buildMenuItems(request));
        }
        if (request.getAlternates() != null) {
            menu.setAlternates(request.getAlternates());
        }
        if (request.getDate() != null) {
            menu.setDate(request.getDate());
        }
        menu.setUpdatedAt(LocalDateTime.now());

        return MenuResponse.from(menuRepository.save(menu));
    }

    // ─── Delete Menu ─────────────────────────────────────────────────────────

    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu", id);
        }
        menuRepository.deleteById(id);
        log.info("Menu with id {} deleted", id);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private MealType parseMealType(String mealTypeStr) {
        try {
            return MealType.valueOf(mealTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid meal type. Accepted values: BREAKFAST, LUNCH, DINNER");
        }
    }

    private List<MenuItem> buildMenuItems(MenuRequest request) {
        if (request.getItems() == null) return new ArrayList<>();
        return request.getItems().stream()
                .map(dto -> MenuItem.builder()
                        .name(dto.getName())
                        .available(dto.isAvailable())
                        .build())
                .toList();
    }
}
