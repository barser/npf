package ru.ospos.npf.officeaddin.service;

import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;

import java.io.File;

public interface OfficeAddinService {

    /**
     * Зарегистрировать загруженный документ в БД и связать его с п/п.
     *
     * @param officeAttachmentMetadata документ MS OFFICE
     * @param pocard платежное поручение
     * @param uploadedFile файл, соответствующий документу
     */
    void bind(OfficeAttachmentMetadata officeAttachmentMetadata, Pocard pocard, File uploadedFile);

    /**
     * Передать п/п на обработку.
     * Учитывая специфику клиентской стороны п/п определяется по загруженному документу MS OFFICE.
     * Ранее этот документ должен был быть привязан к п/п.
     *
     * @param officeAttachmentMetadata документ MS OFFICE
     */
    void process(OfficeAttachmentMetadata officeAttachmentMetadata);
}
