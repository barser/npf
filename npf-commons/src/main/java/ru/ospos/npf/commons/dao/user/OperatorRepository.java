package ru.ospos.npf.commons.dao.user;

import org.springframework.data.repository.CrudRepository;
import ru.ospos.npf.commons.domain.user.Operator;

public interface OperatorRepository extends CrudRepository<Operator, Long> {
}
