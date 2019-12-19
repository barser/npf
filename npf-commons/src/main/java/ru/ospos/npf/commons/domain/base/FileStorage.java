package ru.ospos.npf.commons.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Экземпляры этого класса представляют мета-данные о файлах в файловой системе.
 */
@Getter
@Setter
@Entity
@Table(name = "FILESTORAGES")
public class FileStorage implements Serializable {

    private static final long serialVersionUID = 5417658353954067231L;

    @Id
    @SequenceGenerator(name = "g_file_storage", sequenceName = "FILE_SEQ", schema = "CDM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_file_storage")
    private Integer id;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileStorage)) return false;
        FileStorage entity = (FileStorage) o;
        return Objects.equals(id, entity.id);
    }

    /**
     * Относительный путь к файлу в файловой системе.
     * Папка, относительно которой указан путь, задается настройками системы.
     */
    @Basic(optional = false)
    @Column(name = "PATH", length = 1000)
    private String path;

    /**
     * Расширение файла.
     */
    @Basic(optional = false)
    @Column(name = "EXTENSION", length = 10)
    private String extension;

    /**
     * Прежнее название файла (до регистрации).
     */
    @Column(name = "OLD_FILE_NAME")
    private String oldFileName;

    /**
     * Дата создания.
     */
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationTs;
}
