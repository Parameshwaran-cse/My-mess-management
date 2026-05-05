package com.mess.management.dto.response;

import com.mess.management.entity.Menu;
import com.mess.management.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for menu data returned to clients.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {

    private Long id;
    private String mealType;
    private List<MenuItemDto> items;
    private List<String> alternates;
    private LocalDate date;
    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuItemDto {
        private String name;
        private boolean available;
    }

    /**
     * Convenience factory method to map from a Menu entity.
     */
    public static MenuResponse from(Menu menu) {
        List<MenuItemDto> itemDtos = menu.getItems().stream()
                .map(item -> MenuItemDto.builder()
                        .name(item.getName())
                        .available(item.isAvailable())
                        .build())
                .toList();

        return MenuResponse.builder()
                .id(menu.getId())
                .mealType(menu.getMealType().name())
                .items(itemDtos)
                .alternates(menu.getAlternates())
                .date(menu.getDate())
                .createdAt(menu.getCreatedAt())
                .build();
    }
}
