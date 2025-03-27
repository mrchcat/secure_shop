package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.enums.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewItemDto {
    private UUID articleNumber;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private String imgPath;
    @NotNull
    private MultipartFile image;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Unit unit;
}
