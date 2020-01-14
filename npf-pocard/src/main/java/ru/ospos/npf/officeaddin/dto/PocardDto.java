package ru.ospos.npf.officeaddin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@ToString
public class PocardDto {

    private static final ThreadLocal<DecimalFormat> AMOUNT_FORMATTER =
            ThreadLocal.withInitial(() -> {
                DecimalFormat df = new DecimalFormat("##,###,###,###.00");
                df.setDecimalSeparatorAlwaysShown(true);
                df.setMinimumFractionDigits(2);
                df.setMaximumFractionDigits(2);

                return df;
            });

    private Integer pocardId;

    /**
     * Дата п/п.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    private LocalDate date;

    /**
     * Номер п/п.
     */
    private long number;

    /**
     * Сумма п/п.
     */
    private String amount;

    /**
     * Плательщик.
     */
    private String contragent;

    /**
     * Счет плательщика.
     */
    private String contragentAccount;

    /**
     * ИНН плательщика.
     */
    private String contragentInn;

    /**
     * Назначение платежа.
     */
    private String comments;

    /**
     * Реестры.
     */
    private String linkedFiles;
    private int linkedFilesCount;

    /**
     * Статус документа.
     */
    private String state;
    private int stateCode;

    /**
     * Исполнитель.
     */
    private String executor;

    /**
     * Папка документа.
     */
    private String documentFolder;

    public static Collection<PocardDto> from(Collection<Pocard> pocards) {

        Collection<String> col = Arrays.asList("kasim1.xls, 25.11.2019, 34156KB", "kasim2.xls, 25.12.2019, 34KB");

        return pocards.stream().map(pocard -> PocardDto
                .from(pocard, col,"В работе (рассмотрение)", 100, "Глухова Е.Ю."))
                .collect(Collectors.toUnmodifiableList());
    }

    public static PocardDto from(Pocard pocard, Collection<String> linkedFiles, String state, int stateCode, String executor) {
        var dto = new PocardDto();

        dto.pocardId = pocard.getId();
        dto.number = pocard.getNumber();
        dto.date = pocard.getDate();

        dto.amount = StringUtils.leftPad(AMOUNT_FORMATTER.get().format(pocard.getAmount()), 17);

        dto.comments = StringUtils.abbreviate(pocard.getComments(), 75);
        dto.contragent = StringUtils.abbreviate("Контрагент контрагент контрагент 12345", 32);//pocard.getContragent();

        if (linkedFiles != null) {
            dto.linkedFiles = "TODO";
            dto.linkedFilesCount = linkedFiles.size();
        } else {
            dto.linkedFiles = "";
            dto.linkedFilesCount = 0;
        }

        dto.state = state;
        dto.stateCode = stateCode;
        dto.executor = executor;

        return dto;
    }

    public static PocardDto fromDetailed(Pocard pocard, Collection<String> linkedFiles, String state, int stateCode, String executor) {
        var dto = new PocardDto();

        dto.pocardId = pocard.getId();
        dto.number = pocard.getNumber();
        dto.date = pocard.getDate();

        dto.amount = AMOUNT_FORMATTER.get().format(pocard.getAmount());

        dto.comments = pocard.getComments();
        dto.contragent = "Контрагент контрагент контрагент 12345";

        if (linkedFiles != null) {
            dto.linkedFiles = "TODO";
            dto.linkedFilesCount = linkedFiles.size();
        } else {
            dto.linkedFiles = "";
            dto.linkedFilesCount = 0;
        }

        dto.state = state;
        dto.stateCode = stateCode;
        dto.executor = executor;

        return dto;
    }

}
