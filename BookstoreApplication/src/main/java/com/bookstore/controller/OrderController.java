package com.bookstore.controller;

import com.bookstore.entity.Order;
import com.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
//import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/getOrdersByUserId")
    public List<Order> getOrdersByUserId(@RequestParam("userId") int userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping("/saveOrderWithItems")
    public void saveOrderWithItems(@RequestParam("userId") int userId,
                                   @RequestParam("address") String address,
                                   @RequestParam("itemIds") List<Integer> itemIds,
                                   @RequestParam("itemNums") List<Integer> itemNums,
                                   @RequestParam("district") String district,
                                   @RequestParam("zip") String zip,
                                   @RequestParam("email") String email) {

        // Get the current time in UTC
        Instant now = Instant.now();

        // Convert UTC to IST (Indian Standard Time)
        ZonedDateTime istTime = now.atZone(ZoneId.of("Asia/Kolkata"));

        // Add 8 hours (28800 seconds) to the current IST time
        ZonedDateTime modifiedISTTime = istTime.plusSeconds(28800);

        // Convert ZonedDateTime to Timestamp
        Timestamp datetime = Timestamp.valueOf(modifiedISTTime.toLocalDateTime());

        // Save the order with the modified IST timestamp
        orderService.saveOrderWithItems(userId, address, datetime, itemIds, itemNums, district, zip, email);
    }
}