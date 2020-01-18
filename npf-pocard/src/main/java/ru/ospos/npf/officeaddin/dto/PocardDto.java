package ru.ospos.npf.officeaddin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
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

    public static Collection<PocardDto> from(Collection<Pocard> pocards, Map<Pocard, List<OfficeAttachmentMetadata>> meta) {

        List<PocardDto> result = new ArrayList<>(pocards.size());

        for (Pocard pocard :
                pocards) {

            List<OfficeAttachmentMetadata> attaches = new ArrayList<>();
            for (Pocard p2 :
                    meta.keySet()) {

                if (p2.getId().equals(pocard.getId())) {
                    attaches = meta.get(p2);
                    break;
                }
            }

            result.add(PocardDto.from(pocard, attaches));
        }
        return result;
    }

    public static PocardDto from(Pocard pocard, Collection<OfficeAttachmentMetadata> attaches) {
        var dto = new PocardDto();

        dto.pocardId = pocard.getId();
        dto.number = pocard.getNumber();
        dto.date = pocard.getDate();

        dto.amount = StringUtils.leftPad(AMOUNT_FORMATTER.get().format(pocard.getAmount()), 17);

        dto.comments = StringUtils.abbreviate(pocard.getComments(), 75);
        dto.contragent = StringUtils.abbreviate(pocard.getContragent(), 32);

        dto.linkedFilesCount = attaches.size();
        if (attaches.size() > 0) {
            // TODO!
            dto.linkedFiles = "TODO";
            dto.state = "TODO - state of first(any) attach - Передано на обработку/Готово";
            //dto.stateCode = stateCode;
        } else {
            dto.linkedFiles = "";
            dto.state = "Новое";
        }
        dto.executor = "TODO: исполнитель"; // pocard->actions->holder

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
