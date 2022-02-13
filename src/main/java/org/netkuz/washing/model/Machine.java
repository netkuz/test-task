package org.netkuz.washing.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "machine", schema = "public")
public class Machine {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    /**
     * Serial number
     */
    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;
    /**
     * {@link Model}
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;
    /**
     * {@link Program}
     */
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    /**
     * {@link Status}
     */
    @NotNull
    @Enumerated
    @Column(name = "status")
    private Status status;
    /**
     * {@link Mode}
     */
    @NotNull
    @Enumerated
    @Column(name = "mode")
    private Mode mode;
    /**
     * {@link Type}
     */
    @NotNull
    @Enumerated
    @Column(name = "type_")
    private Type type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var machine = (Machine) o;
        return id != null && Objects.equals(id, machine.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
