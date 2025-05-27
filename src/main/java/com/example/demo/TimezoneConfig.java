package com.example.demo;

import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TimezoneConfig {

    @Value("${app.timezone:UTC}")
    private String applicationTimezone;

    @PostConstruct
    public void init() {
        try {
            // Parse timezone from environment variable
            String timezoneId = parseTimezoneId(applicationTimezone);
            
            // Set the default timezone for the entire application
            TimeZone timezone = TimeZone.getTimeZone(timezoneId);
            TimeZone.setDefault(timezone);
            
            // Also set system property for JVM
            System.setProperty("user.timezone", timezoneId);
            
            log.info("Application timezone set to: {} ({})", timezoneId, timezone.getDisplayName());
            
        } catch (Exception e) {
            log.error("Failed to set timezone: {}. Falling back to UTC", applicationTimezone, e);
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            System.setProperty("user.timezone", "UTC");
        }
    }
    
    private String parseTimezoneId(String timezoneString) {
        if (timezoneString == null || timezoneString.trim().isEmpty()) {
            return "UTC";
        }
        
        // Handle UTC offset format (UTC-5, UTC+3, etc.)
        if (timezoneString.toUpperCase().startsWith("UTC")) {
            String offset = timezoneString.substring(3); // Remove "UTC"
            
            if (offset.isEmpty()) {
                return "UTC";
            }
            
            // Convert UTC-5 to GMT-05:00 format
            if (offset.startsWith("-") || offset.startsWith("+")) {
                int hours = Integer.parseInt(offset);
                return String.format("GMT%+03d:00", hours);
            }
        }
        
        // Handle direct timezone IDs (America/New_York, Europe/Madrid, etc.)
        try {
            ZoneId.of(timezoneString);
            return timezoneString;
        } catch (Exception e) {
            log.warn("Invalid timezone format: {}. Using UTC", timezoneString);
            return "UTC";
        }
    }
    
    public String getCurrentTimezone() {
        return TimeZone.getDefault().getID();
    }
}