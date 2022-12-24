package ru.spacelord.sneakershop.sneakershop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Images {
    private static final String SEQ_NAME = "images_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME,sequenceName = SEQ_NAME,allocationSize = 1)
    private Long id;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
