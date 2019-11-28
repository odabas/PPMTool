package io.odabas.ppmtool.repositories;

import io.odabas.ppmtool.domain.ProjectTask;
import org.hibernate.internal.util.StringHelper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long  > {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    ProjectTask findByProjectSequence( String seq);
}
