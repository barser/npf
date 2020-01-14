package ru.ospos.npf.officeaddin.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * DTO для операции поиска платежных поручений.
 */
@Data
public class Search {

    // Идентификатор поискового запроса
    private UUID id;
    // Состояние поискового запроса
    private String state;

    // Параметры поиска
    private String amount;
    private String number;
    private String date;
    private String contragent;

    // Запомненные разобранные значения соответствующих параметров поиска
    private Long parsedNumber;
    private BigDecimal parsedAmount;
    private LocalDate parsedDate;

    // Найденные платежные поручения
    private Collection<PocardDto> foundPocards;

    public Long getParsedNumber() {

        if (parsedNumber != null) return parsedNumber;
        if (StringUtils.isBlank(number)) return null;

        try {
            parsedNumber = Long.parseLong(number);
            return parsedNumber;
        } catch (Exception e) {
            return null;
        }
    }

    public BigDecimal getParsedAmount() {

        if (parsedAmount != null) return parsedAmount;
        if (StringUtils.isBlank(amount)) return null;

        try {
            parsedAmount = new BigDecimal(amount);
            return parsedAmount;
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate getParsedDate() {

        if (parsedDate != null) return parsedDate;
        if (StringUtils.isBlank(date)) return null;

        if (date == null) {
            return null;
        }

        var formatter = buildFormatter(date);
        try {
            parsedDate = LocalDate.parse(date, formatter);
            return parsedDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Подбор подходящих правил форматирования для произвольной строки с датой.
     * @param rawDate строка с датой
     * @return экземпляр DateTimeFormatter с подходящим параметром pattern.
     */
    private static DateTimeFormatter buildFormatter(String rawDate) {

        String pattern = "dd.MM.yyyy";
        if (Pattern.matches("\\d{6}", rawDate)) {
            pattern = "ddMMyy";
        }
        else if (Pattern.matches("\\d{8}", rawDate)) {
            pattern = "ddMMyyyy";
        }
        else if (Pattern.matches("\\d{2}.\\d{2}.\\d{2}", rawDate)) {
            pattern = "dd.MM.yy";
        }
        return DateTimeFormatter.ofPattern(pattern);
    }
}
