package io.odabas.ppmtool.services;

import io.odabas.ppmtool.domain.Project;
import io.odabas.ppmtool.exceptions.ProjectIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.odabas.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService  {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return  projectRepository.save(project);
        }
        catch (Exception ex){
            throw  new ProjectIdException("ProjectId '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = checkAndHandleExistProject(projectId,"ProjectId '" + projectId + "' doesn't exist");
        return  project;
    }

    public Iterable<Project> findAllProjects() {
         return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectIdentiier){
        String errorMessage = "Cannot delete project with id  '" + projectIdentiier + "' Because this project doesn't exist";
        Project project = checkAndHandleExistProject(projectIdentiier,errorMessage);
        projectRepository.delete(project);
    }

    public Project checkAndHandleExistProject(String projectIdentifier,String errorMessage){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project==null){
            throw  new ProjectIdException(errorMessage);
        }
        return project;
    }
}
