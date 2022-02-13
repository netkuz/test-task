package org.netkuz.washing.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.netkuz.washing.model.Machine;
import org.netkuz.washing.model.Machine_;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterMachine {
    private UUID modelId;
    private String serialNumber;
    private Set<Status> statuses;
    private Set<Mode> modes;
    private Set<Type> types;

    /**
     * Get filter specification
     *
     * @return {@link Specification}
     */
    public Specification<Machine> getSpecification() {
        return (root, query, cb) -> {
            final var filters = new ArrayList<Predicate>();
            if (Objects.nonNull(modelId)) {
                filters.add(root.get(Machine_.model).in(modelId));
            }
            if (StringUtils.hasText(serialNumber)) {
                filters.add(cb.like(cb.lower(root.get(Machine_.serialNumber)), "%" + serialNumber.toLowerCase() + "%"));
            }
            if (!CollectionUtils.isEmpty(statuses)) {
                filters.add(root.get(Machine_.status).in(statuses));
            }
            if (!CollectionUtils.isEmpty(modes)) {
                filters.add(root.get(Machine_.mode).in(modes));
            }
            if (!CollectionUtils.isEmpty(types)) {
                filters.add(root.get(Machine_.type).in(types));
            }
            return cb.and(filters.toArray(new Predicate[0]));
        };
    }
}
