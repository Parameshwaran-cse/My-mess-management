package com.mess.management.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Represents a single food item within a menu.
 * Embedded inside Menu as a collection.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    private String name;

    @Builder.Default
    private boolean available = true;
}
