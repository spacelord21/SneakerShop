package ru.spacelord.sneakershop.sneakershop.dto;

import lombok.*;
import ru.spacelord.sneakershop.sneakershop.domain.Category;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private List<Category> categories;
    private int amount;
}
