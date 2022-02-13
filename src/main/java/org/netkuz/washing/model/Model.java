package org.netkuz.washing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "model", schema = "public")
public class Model {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    /**
     * Name
     */
    @Column(name = "model_name", unique = true, nullable = false)
    private String name;
    /**
     * Description
     */
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var model = (Model) o;
        return id != null && Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
