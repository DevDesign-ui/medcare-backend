package com.medcare.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InvoiceNumberGenerator {

    private final AtomicInteger counter = new AtomicInteger(1);

    public String generate() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequence = String.format("%04d", counter.getAndIncrement());
        return "FAC-" + datePart + "-" + sequence;
    }
}
