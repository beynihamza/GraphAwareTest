package com.graphaware.pizzeria;

import com.graphaware.pizzeria.model.Pizza;
import com.graphaware.pizzeria.model.PizzeriaUser;
import com.graphaware.pizzeria.model.Purchase;
import com.graphaware.pizzeria.model.PurchaseState;
import com.graphaware.pizzeria.repository.PizzeriaUserRepository;
import com.graphaware.pizzeria.repository.PurchaseRepository;
import com.graphaware.pizzeria.security.PizzeriaUserPrincipal;
import com.graphaware.pizzeria.service.DiscountService;
import com.graphaware.pizzeria.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {


    @Mock
    private PizzeriaUserRepository userRepository;
    @Mock
    private PurchaseRepository purchaseRepository;
    private PizzeriaUser currentUser;
    private PurchaseService purchaseService;
    @Autowired
    private List<? extends DiscountService> discountServiceList;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(purchaseRepository, userRepository, discountServiceList);
        currentUser = new PizzeriaUser();
        currentUser.setName("Papa");
        currentUser.setId(666L);
        currentUser.setRoles(Collections.emptyList());
        currentUser.setEmail("abc@def.com");
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        PizzeriaUserPrincipal userPrincipal = new PizzeriaUserPrincipal(currentUser);
        Mockito.when(authentication.getPrincipal()).thenReturn(userPrincipal);
    }

    @Test
    void should_not_apply_three_pizzas_discount() {
        //Should apply on the pineapple discount Code.
        Purchase p = new Purchase();
        p.setState(PurchaseState.ONGOING);
        Pizza one = new Pizza();
        one.setId(666L);
        one.setPrice(15.0);
        one.setToppings(Arrays.asList("pineapple"));
        Pizza two = new Pizza();
        two.setId(43L);
        two.setPrice(10.0);
        two.setToppings(Arrays.asList("notPineapple"));
        Pizza three = new Pizza();
        three.setId(43L);
        three.setPrice(10.0);
        three.setToppings(Arrays.asList("notPineapple"));
        p.setPizzas(Arrays.asList(one, two, three));
        when(purchaseRepository.findFirstByStateEquals(any()))
                .thenReturn(p);
        when(purchaseRepository.findById(any()))
                .thenReturn(Optional.of(p));
        purchaseService.pickPurchase();
        purchaseService.completePurchase(p.getId());
        assertThat(p.getAmount()).isEqualByComparingTo(33.0);
    }

    @Test
    void should_apply_three_pizzas_discount() {
        Purchase p = new Purchase();
        p.setState(PurchaseState.ONGOING);
        Pizza one = new Pizza();
        one.setId(666L);
        one.setPrice(15.0);
        one.setToppings(Arrays.asList("notPineapple"));
        Pizza two = new Pizza();
        two.setId(43L);
        two.setPrice(10.0);
        two.setToppings(Arrays.asList("notPineapple"));
        Pizza three = new Pizza();
        three.setId(43L);
        three.setPrice(10.0);
        three.setToppings(Arrays.asList("notPineapple"));
        p.setPizzas(Arrays.asList(one, two, three));
        when(purchaseRepository.findFirstByStateEquals(any()))
                .thenReturn(p);
        when(purchaseRepository.findById(any()))
                .thenReturn(Optional.of(p));
        purchaseService.pickPurchase();
        purchaseService.completePurchase(p.getId());
        assertThat(p.getAmount()).isEqualByComparingTo(25.0);
    }
}
