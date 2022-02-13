package org.netkuz.washing.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.netkuz.washing.model.Program;
import org.netkuz.washing.model.Program_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterProgram {
    private UUID modelId;
    private String name;
    private String description;

    /**
     * Get filter specification
     *
     * @return {@link Specification}
     */
    public Specification<Program> getSpecification() {
        return (root, query, cb) -> {
            final var filters = new ArrayList<Predicate>();
            if (Objects.nonNull(modelId)) {
                filters.add(root.get(Program_.model).in(modelId));
            }
            if (StringUtils.hasText(name)) {
                filters.add(cb.like(cb.lower(root.get(Program_.name)), "%" + name.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(description)) {
                filters.add(cb.like(cb.lower(root.get(Program_.description)), "%" + description.toLowerCase() + "%"));
            }
            return cb.and(filters.toArray(new Predicate[0]));
        };
    }
}
