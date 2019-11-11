package io.odabas.ppmtool.services;

import io.odabas.ppmtool.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.odabas.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService  {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        return  projectRepository.save(project);
    }
}
