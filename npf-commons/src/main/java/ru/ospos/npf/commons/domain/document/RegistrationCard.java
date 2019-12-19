package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Регистрационная карта документа.
 */
@Getter
@Setter
@Entity
@Table(name = "REGISTRATION_CARDS")
public class RegistrationCard implements Serializable {

    private static final long serialVersionUID = 2562876180854522677L;

    @Id
    @SequenceGenerator(name = "g_registration_card", sequenceName = "REGISTRATION_CARD_SEQ", schema = "CDM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_registration_card")
    private Integer id;

    /**
     *
     */
    @Column(name = "CREATED_DATE")
    private LocalDateTime creationTs;

    /**
     * Статус карточки.
     */
    @Column(name = "STATUS")
    private Integer state;

    /**

     * DOC_LINKED	VARCHAR2(32 BYTE)	Yes		4	связанный документ
     * ANSWER_TO_NUM	VARCHAR2(32 BYTE)	Yes		5	в ответ на (номер)
     * ANSWER_TO_DATE	DATE	Yes		6	в ответ на (дата)
     * DEADLINE	DATE	Yes		7	срок исполнения
     * REG_CARD_TEMP_ID	NUMBER(10,0)	Yes		8	registration_card_temp_id (используется для импорта)
     * MANUAL	NUMBER(1,0)	Yes	0	9	признак того, что файл был привязан к карточке вручную
     * CREATED_DATE	DATE	Yes	sysdate 	10	дата создания записи
     * FK_DOCUMENT_TEMPLATE	NUMBER(10,0)	Yes	3	11	ссылка на шаблон по которому была создана карточка
     * KI	NUMBER(1,0)	Yes	0 	12	признак, является ли конфиденциальной информацией
     * COUNT_SHEETS_DOC	NUMBER(4,0)	Yes		13	кол-во листов документа
     * COUNT_SHEETS_APP	NUMBER(4,0)	Yes		14	кол-во листов приложения
     * THEME_LETTER	VARCHAR2(200 BYTE)	Yes		15	тема письма
     * CONTENT_LETTER	VARCHAR2(4000 BYTE)	Yes		16	содержимое письма
     * RECIPIENT_FIO	VARCHAR2(200 BYTE)	Yes		17	ФИО получателя полностью (обращение в письме)
     * STATUS	NUMBER(10,0)	Yes		18	Статус карточки
     * FK_DOCUMENT_PRIORITY	NUMBER(10,0)	No	1 	19	Приоритет исполнения документа (по-умолчанию Обычный)
     * FK_DISPATCH	NUMBER(10,0)	Yes		20	ссылка на отправление, связанное с исходящим документом
     * FK_DOCUMENT_SOURCE	NUMBER(10,0)	Yes		21	Источник (канал) поступления документа
     * FK_OPERATOR	NUMBER(10,0)	Yes		22	Автор регистрационной карточки
     * FK_OFFICE	NUMBER(10,0)	Yes		23	Компания, под которой сотрудник создает рег. карточку, нужно для генерации рег. номера
     * FROM_ORG_PERSON	NUMBER(10,0)	Yes		24	Тип поля От кого
     * RECIPIENT	NUMBER(10,0)	Yes		25	Тип поля Адресат
     * FROM_ORG_PERSON_TYPE	NUMBER(1,0)	Yes		26	Тип поля От кого, 1-физ.лицо, 2-юр.лицо, 3-свой тип
     * RECIPIENT_TYPE	NUMBER(1,0)	Yes		27	Тип поля Адресат, 1-физ.лицо, 2-юр.лицо, 3-свой тип
     * FK_COMPANY	NUMBER(10,0)	Yes		28	Компания, владелец документа
     * FK_EXEC	NUMBER(10,0)	Yes		29	Исполнитель
     * TO_DIRECT	NUMBER(10,0)	Yes		30
     * MOTIWNUM	VARCHAR2(20 BYTE)	Yes		31	Регистрационный номер документа в системе Мотив
     * MOTIWDATE	DATE	Yes		32	Дата регистрации документа в системе Мотив
     */
}
