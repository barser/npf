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

    /**
     * Создание dto из экземпляров Pocard, а также дополнительных данных о привязанных к ним офисных документов.
     * Внутри вызывается {@link PocardDto#from(Pocard, boolean, Collection)}.
     *
     * @param pocards платежные поручения, экземпляры Pocard
     * @param meta дополнительные данные о привязанных платежных поручениях. Ключ - идентификатор (id) платежного поручения.
     * @return Коллекция dto для передачи клиенту
     */
    public static Collection<PocardDto> from(Collection<Pocard> pocards, Map<Integer, List<OfficeAttachmentMetadata>> meta) {

        List<PocardDto> result = new ArrayList<>(pocards.size());

        for (Pocard pocard :
                pocards) {

            List<OfficeAttachmentMetadata> pocardAttaches = new ArrayList<>();
            for (Integer pId :
                    meta.keySet()) {

                if (pId != null && pId.equals(pocard.getId())) {
                    pocardAttaches = meta.get(pId);
                    break;
                }
            }
            result.add(PocardDto.from(pocard, false, pocardAttaches));
        }
        return result;
    }

    /**
     * Создание dto из экземпляра Pocard и привязанных к нему офисных документов (если есть).
     *
     * @param pocard платежное поручение, экземпляр Pocard.
     * @param detailed признак того, что dto должен содержать подробную детализированную информацию.
     * @param attaches коллекция офисных документов, которые привязаны к платежному поручению
     * @return dto для передачи клиенту.
     */
    public static PocardDto from(Pocard pocard, boolean detailed, Collection<OfficeAttachmentMetadata> attaches) {
        var dto = new PocardDto();

        dto.pocardId = pocard.getId();
        dto.number = pocard.getNumber();
        dto.date = pocard.getDate();

        if (detailed) {
            dto.amount = AMOUNT_FORMATTER.get().format(pocard.getAmount());
            dto.comments = pocard.getComments();
            dto.contragent = pocard.getContragent();
            dto.contragentAccount = pocard.getFromAccount();
            dto.contragentInn = pocard.getFromInn();
            dto.stateCode = pocard.getStatus();

            if (pocard.getTreeNodes().size() > 0) {
                // Первую папку выводим в подробностях.
                // TODO - а быть может выводить все папки, в которых находится документ?
                dto.documentFolder = pocard.getTreeNodes()
                        .iterator().next()
                        .getTitle();
            }

        } else {
            dto.amount = StringUtils.leftPad(AMOUNT_FORMATTER.get().format(pocard.getAmount()), 17);
            dto.comments = StringUtils.abbreviate(pocard.getComments(), 75);
            dto.contragent = StringUtils.abbreviate(pocard.getContragent(), 32);
        }

        dto.linkedFilesCount = attaches.size();
        dto.linkedFiles = "";
        dto.state = "Новое";

        for (OfficeAttachmentMetadata oam : attaches) {

            if (detailed) {
                //noinspection StringConcatenationInLoop
                dto.linkedFiles += oam.getDetails();
            }

            switch (oam.getState()) {
                case ON_EXECUTION:
                    dto.state = "Передано на исп.";
                    break;
                case BINDED:
                    dto.state = "Выполн. привязка";
                    break;
                case DONE:
                default:
                    dto.state = "Исполнено";
            }
        }

        if (pocard.getHold() != null && pocard.getHold().getOperator() != null) {
            dto.executor = pocard.getHold().getOperator().getLogin();
        } else {
            dto.executor = "";
        }
        return dto;
    }
}
