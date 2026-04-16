package com.food.backend.config;

import com.food.backend.model.MenuItem;
import com.food.backend.model.Restaurant;
import com.food.backend.model.User;
import com.food.backend.repository.MenuItemRepository;
import com.food.backend.repository.RestaurantRepository;
import com.food.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, RestaurantRepository restaurantRepo, MenuItemRepository menuRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = userRepo.findByEmail("admin@food.com");
            if (admin == null) {
                admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@food.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepo.save(admin);
            } else if (!"ADMIN".equals(admin.getRole())) {
                admin.setRole("ADMIN");
                userRepo.save(admin);
            }

            if (restaurantRepo.count() == 0) {
                Restaurant r1 = new Restaurant();
                r1.setName("Pizza Palace");
                r1 = restaurantRepo.save(r1);

                MenuItem m1 = new MenuItem();
                m1.setName("Margherita Pizza");
                m1.setPrice(new BigDecimal("12.99"));
                m1.setRestaurant(r1);
                menuRepo.save(m1);

                MenuItem m2 = new MenuItem();
                m2.setName("Pepperoni Pizza");
                m2.setPrice(new BigDecimal("14.99"));
                m2.setRestaurant(r1);
                menuRepo.save(m2);

                Restaurant r2 = new Restaurant();
                r2.setName("Burger Barn");
                r2 = restaurantRepo.save(r2);

                MenuItem m3 = new MenuItem();
                m3.setName("Classic Cheeseburger");
                m3.setPrice(new BigDecimal("8.99"));
                m3.setRestaurant(r2);
                menuRepo.save(m3);
                
                MenuItem m4 = new MenuItem();
                m4.setName("Fries");
                m4.setPrice(new BigDecimal("3.99"));
                m4.setRestaurant(r2);
                menuRepo.save(m4);
            }

            if (restaurantRepo.findByNameContainingIgnoreCase("A2B").isEmpty()) {
                Restaurant r3 = new Restaurant();
                r3.setName("A2B (Adyar Ananda Bhavan)");
                r3 = restaurantRepo.save(r3);

                MenuItem m5 = new MenuItem();
                m5.setName("Idli Sambar");
                m5.setPrice(new BigDecimal("40.00"));
                m5.setRestaurant(r3);
                menuRepo.save(m5);

                MenuItem m6 = new MenuItem();
                m6.setName("Masala Dosa");
                m6.setPrice(new BigDecimal("80.00"));
                m6.setRestaurant(r3);
                menuRepo.save(m6);

                MenuItem m7 = new MenuItem();
                m7.setName("Medu Vada");
                m7.setPrice(new BigDecimal("30.00"));
                m7.setRestaurant(r3);
                menuRepo.save(m7);
            }

            if (restaurantRepo.findByNameContainingIgnoreCase("Saravana Bhavan").isEmpty()) {
                Restaurant r4 = new Restaurant();
                r4.setName("Saravana Bhavan");
                r4 = restaurantRepo.save(r4);

                MenuItem m8 = new MenuItem();
                m8.setName("Filter Coffee");
                m8.setPrice(new BigDecimal("25.00"));
                m8.setRestaurant(r4);
                menuRepo.save(m8);

                MenuItem m9 = new MenuItem();
                m9.setName("Ghee Pongal");
                m9.setPrice(new BigDecimal("60.00"));
                m9.setRestaurant(r4);
                menuRepo.save(m9);

                MenuItem m10 = new MenuItem();
                m10.setName("Rava Dosa");
                m10.setPrice(new BigDecimal("90.00"));
                m10.setRestaurant(r4);
                menuRepo.save(m10);
            }
        };
    }
}
