package org.netkuz.washing.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.netkuz.washing.model.enumeration.Detergent;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "program", schema = "public")
public class Program {
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
    @Column(name = "program_name", nullable = false)
    private String name;
    /**
     * {@link Model}
     */
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
    /**
     * Temperature
     */
    @Column(name = "temperature")
    private int temperature;
    /**
     * Detergent
     */
    @NotNull
    @Enumerated
    @Column(name = "detergent")
    private Detergent detergent;
    /**
     * Weight
     */
    @Column(name = "weight")
    private int weight;
    /**
     * Spin
     */
    @Column(name = "spin")
    private int spin;
    /**
     * Description
     */
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var program = (Program) o;
        return id != null && Objects.equals(id, program.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
