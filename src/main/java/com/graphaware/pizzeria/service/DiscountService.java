package com.graphaware.pizzeria.service;

import com.graphaware.pizzeria.model.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class DiscountService implements Comparable<DiscountService> {
    int priority=0;
    public abstract double applyDiscount(List<Pizza> pizzas);
    public abstract boolean isDiscountApplicable(List<Pizza> pizzas);
    public void setPriority(int priority) {
        this.priority = priority;
    };
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DiscountService o) {
        if(this.getPriority() < o.getPriority()) {
            return -1;
        } else if(this.getPriority() > o.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}
