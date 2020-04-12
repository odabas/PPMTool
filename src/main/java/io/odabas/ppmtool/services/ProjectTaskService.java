package io.odabas.ppmtool.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.odabas.ppmtool.domain.Backlog;
import io.odabas.ppmtool.domain.Project;
import io.odabas.ppmtool.domain.ProjectTask;
import io.odabas.ppmtool.exceptions.ProjectNotFoundException;
import io.odabas.ppmtool.repositories.BacklogRepository;
import io.odabas.ppmtool.repositories.ProjectRepository;
import io.odabas.ppmtool.repositories.ProjectTaskRepository;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService  {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask){
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BacklogSequence = backlog.getPTSequence();
            backlog.setPTSequence(++BacklogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //priority
            if(projectTask.getPriority()==0 || projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }
            //status
            if(projectTask.getStatus()=="" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        }
        catch(Exception e){
            throw new ProjectNotFoundException("Project " +projectIdentifier + " Not Found!");
        }



    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);
        if(project == null){
            throw new ProjectNotFoundException("Project with ID : " + id+" does not exist");
        }
        return  projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id,String sequence){
        //make sure searching on the right backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if(backlog==null)
            throw  new ProjectNotFoundException("Project with ID:"+backlog_id+" doesn't exist");

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null)
            throw new  ProjectNotFoundException("Project Task with sequence: " + sequence + " doesn't exist");

        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new  ProjectNotFoundException("Project with ID:"+backlog_id+" has no project task with sequence: "+sequence);
        return projectTaskRepository.findByProjectSequence(sequence);

    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask ,String backlog_id ,String pt_id){
        ProjectTask projectTask  = findPTByProjectSequence(backlog_id,pt_id);
         projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence(String backlog_id , String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
        projectTaskRepository.delete(projectTask);
    }
}
