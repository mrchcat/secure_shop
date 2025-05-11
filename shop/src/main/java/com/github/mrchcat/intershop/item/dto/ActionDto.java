package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.enums.CartAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ActionDto {
    CartAction action;
}
