package com.graphaware.pizzeria.service.impl;

import com.graphaware.pizzeria.model.Pizza;
import com.graphaware.pizzeria.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PineappleDiscountServiceImpl extends DiscountService {


    public PineappleDiscountServiceImpl() {
        super();
        setPriority(1);
    }

    @Override
    public double applyDiscount(List<Pizza> pizzas) {
        double totalPrice = 0;
        for (Pizza pizza : pizzas) {
            if (pizza.getToppings().contains("pineapple")) {
                totalPrice += pizza.getPrice();
            } else {
                totalPrice += pizza.getPrice() * 0.9;
            }
        }
        return totalPrice;
    }

    @Override
    public boolean isDiscountApplicable(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            if (pizza.getToppings().contains("pineapple")) {
                return true;
            }
        }
        return false;
    }
}
