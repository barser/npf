package ru.ospos.npf;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class TestDto {

    private String aString;
    private Character aChar;

    private Long aLong;
    private Double aDouble;

    private LocalDate aDate;
    private LocalDateTime aDateTime;

    private BigDecimal aBigDecimal;
    private Boolean aBoolean;

    private Collection<TestDto> aCollection;

}
