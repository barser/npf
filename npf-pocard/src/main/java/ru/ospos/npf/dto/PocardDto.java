package ru.ospos.npf.dto;

import lombok.Getter;
import lombok.ToString;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@ToString
public class PocardDto {

    private Integer id;
    private long number;
    private LocalDate date;
    private BigDecimal amount;
    private String contragent;
    private String comments;
    private String linkedFiles;
    private String state;
    private int stateCode;
    private String executor;

    public static Collection<PocardDto> from(Collection<Pocard> pocards) {
        return pocards.stream().map(pocard -> PocardDto
                .from(pocard, "kasim1.xls, 25.11.2019, 34156KB; kasim2.xls, 25.12.2019, 34KB ",
                "В работе (рассмотрение)", 100, "Глухова Е.Ю."))
                .collect(Collectors.toUnmodifiableList());
    }

    public static PocardDto from(Pocard pocard, String linkedFiles, String state, int stateCode, String executor) {
        var dto = new PocardDto();

        dto.id = pocard.getId();
        dto.number = pocard.getNumber();
        dto.date = pocard.getDate();
        dto.amount = pocard.getAmount();
        dto.contragent = pocard.getContragent();
        dto.comments = pocard.getComments();
        dto.linkedFiles = linkedFiles;
        dto.state = state;
        dto.stateCode = stateCode;
        dto.executor = executor;

        return dto;
    }
}
