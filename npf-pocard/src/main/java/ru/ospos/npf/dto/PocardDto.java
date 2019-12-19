package ru.ospos.npf.dto;

import lombok.Getter;
import lombok.ToString;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
public class PocardDto {

    private Integer id;
    private int number;
    private LocalDate date;
    private String comments;
    private BigDecimal amount;

    public static Collection<PocardDto> from(Collection<Pocard> pocards) {
        return new ArrayList<>();
    }

    public static PocardDto from(Pocard pocard) {
        return new PocardDto();
    }
}
