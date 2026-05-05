package com.mess.management.controller;

import com.mess.management.dto.request.MenuRequest;
import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.MenuResponse;
import com.mess.management.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for mess menu management.
 *
 * Access rules (enforced by SecurityConfig):
 *  GET endpoints  → Any authenticated user
 *  POST/PUT/DELETE → ADMIN only
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * GET /api/menus
     * Returns all available menus.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getAllMenus() {
        return ResponseEntity.ok(ApiResponse.success(menuService.getAllMenus()));
    }

    /**
     * GET /api/menus/today
     * Returns all menus available for today.
     */
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getTodaysMenus() {
        return ResponseEntity.ok(
                ApiResponse.success("Today's menus", menuService.getTodaysMenus()));
    }

    /**
     * GET /api/menus/{id}
     * Returns a specific menu by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuResponse>> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(menuService.getMenuById(id)));
    }

    /**
     * POST /api/menus  [ADMIN ONLY]
     * Creates a new menu entry for a specific date and meal type.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MenuResponse>> createMenu(
            @Valid @RequestBody MenuRequest request) {
        MenuResponse created = menuService.createMenu(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Menu created successfully.", created));
    }

    /**
     * PUT /api/menus/{id}  [ADMIN ONLY]
     * Updates an existing menu's items, alternates, or date.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuResponse>> updateMenu(
            @PathVariable Long id,
            @RequestBody MenuRequest request) {
        MenuResponse updated = menuService.updateMenu(id, request);
        return ResponseEntity.ok(ApiResponse.success("Menu updated successfully.", updated));
    }

    /**
     * DELETE /api/menus/{id}  [ADMIN ONLY]
     * Deletes a menu by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Menu deleted successfully."));
    }
}
