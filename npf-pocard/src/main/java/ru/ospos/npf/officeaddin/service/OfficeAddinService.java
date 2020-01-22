package ru.ospos.npf.officeaddin.service;

import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.officeaddin.domain.FileOperation;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OfficeAddinService {

    /**
     * Поиск подходящих платежек по одному или нескольким параметрам.
     *
     * @param number номер п/п
     * @param date дата п/п
     * @param amount сумма
     * @param contragent от кого
     * @return коллекция платежных поручений
     */
    List<Pocard> search(Long number, LocalDate date, BigDecimal amount, String contragent);

    /**
     * Зарегистрировать загруженный документ в БД и связать его с п/п.
     *
     * @param pocard платежное поручение
     * @param uploadedFile файл, соответствующий документу
     * @param fileOperation метаданные об операции над файлом
     */
    void bind(Pocard pocard, File uploadedFile, FileOperation fileOperation);

    /**
     * Передать п/п на обработку.
     *
     * @param pocard платежное поручение
     */
    void process(Pocard pocard);
}
