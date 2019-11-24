package io.odabas.ppmtool.repositories;

import io.odabas.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

public interface BacklogRepository extends CrudRepository<Backlog,Long>
{
}
