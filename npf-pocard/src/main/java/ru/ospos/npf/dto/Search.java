package ru.ospos.npf.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Data
public class Search {

    private UUID id;
    private String state;

    private BigDecimal amount;
    private long number;
    private LocalDate date;
    private String contragent;

    private Collection<PocardDto> foundPocards;
}
