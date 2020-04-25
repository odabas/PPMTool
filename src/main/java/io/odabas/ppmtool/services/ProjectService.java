package io.odabas.ppmtool.services;

import io.odabas.ppmtool.domain.Backlog;
import io.odabas.ppmtool.domain.Project;
import io.odabas.ppmtool.domain.User;
import io.odabas.ppmtool.exceptions.ProjectIdException;
import io.odabas.ppmtool.exceptions.ProjectNotFoundException;
import io.odabas.ppmtool.repositories.BacklogRepository;
import io.odabas.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.odabas.ppmtool.repositories.ProjectRepository;

import java.security.Principal;

@Service
public class ProjectService  {

    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project ,String username){

        if(project.getId()!=null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject != null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }

        try{
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            }
            else if (project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
            }
            return  projectRepository.save(project);
        }
        catch (Exception ex){
            throw  new ProjectIdException("ProjectId '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    //Only want to return the project if the user looking for its owner
    public Project findProjectByIdentifier(String projectId,String username){
        Project project = checkAndHandleExistProject(projectId,"ProjectId '" + projectId + "' doesn't exist");
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return  project;
    }

    public Iterable<Project> findAllProjects(String username) {
         return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectIdentifier, String username){
        projectRepository.delete(findProjectByIdentifier(projectIdentifier,username));
    }

    public Project checkAndHandleExistProject(String projectIdentifier,String errorMessage){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project==null){
            throw  new ProjectIdException(errorMessage);
        }
        return project;
    }
}
