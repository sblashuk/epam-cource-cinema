package com.epam.cinema.service;

import com.epam.cinema.dto.UserDiscount;

import java.util.List;

public interface DiscountService {

    Long save (UserDiscount userDiscount);

    List<UserDiscount> getAll();
}
