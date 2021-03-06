package com.alf.webshop.webshop.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alf.webshop.webshop.model.response.UserResponse;
import com.alf.webshop.webshop.service.email.CustomMailSender;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomMailSender customMailSender;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 86400000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 864000000)
    public void handleInactiveUsers() {
        long millis = System.currentTimeMillis();
        int tenDaysInMillis = 864000000;
        java.sql.Date tenDaysBeforeToday = new java.sql.Date(millis - tenDaysInMillis);
        int eightDaysInMillis = 691200000;
        java.sql.Date eightDaysBeforeToday = new java.sql.Date(millis - eightDaysInMillis);
        ArrayList<User> inactiveUsers = userRepository.findUsersByLastLoginTimeBeforeAndRoleIsNot(tenDaysBeforeToday, Role.DELETED);
        ArrayList<User> notifiableUsers = userRepository.findUsersByLastLoginTimeBeforeAndRoleIsNot(eightDaysBeforeToday, Role.DELETED);

        log.info("Deleting inactive users...");
        for (User inactiveUser : inactiveUsers) {
            log.info("inactive user detected: " + new UserResponse(inactiveUser));
            userService.disableUser(inactiveUser);
        }

        log.info("Notifying inactive users...");
        for (User notifiableUser : notifiableUsers) {
            log.info("notifiable user detected: " + new UserResponse(notifiableUser));
            customMailSender.sendNotificationEmail(notifiableUser.getEmail());
        }

    }
}