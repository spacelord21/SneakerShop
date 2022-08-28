package ru.spacelord.sneakershop.sneakershop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spacelord.sneakershop.sneakershop.domain.Product;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {
    private BigDecimal sum;
    private List<Integer> products;
}
