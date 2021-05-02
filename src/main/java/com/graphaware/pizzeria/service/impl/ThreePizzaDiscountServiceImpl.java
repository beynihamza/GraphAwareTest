package com.graphaware.pizzeria.service.impl;

import com.graphaware.pizzeria.model.Pizza;
import com.graphaware.pizzeria.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreePizzaDiscountServiceImpl extends DiscountService {


    public ThreePizzaDiscountServiceImpl() {
        super();
        setPriority(2);
    }

    @Override
    public double applyDiscount(List<Pizza> pizzas) {
        Double minPrice = Double.MAX_VALUE;
        double totalPrice = 0;
        boolean reduceLowestPizza=false;
        for (Pizza pizza : pizzas) {
            if (pizza.getPrice() <= minPrice) {
                minPrice = pizza.getPrice();
            }
        }
        for (Pizza pizza : pizzas) {
            if (pizza.getPrice() == minPrice && !reduceLowestPizza) {
                reduceLowestPizza = true;
            } else {
                totalPrice += pizza.getPrice();
            }
        }
        return totalPrice;
    }

    @Override
    public boolean isDiscountApplicable(List<Pizza> pizzas) {
        return pizzas.size() == 3;
    }
}
