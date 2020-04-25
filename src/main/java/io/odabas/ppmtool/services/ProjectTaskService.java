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
import org.omg.CORBA.WStringSeqHelper;
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

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask ,String username){

            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
            projectTask.setBacklog(backlog);
            Integer BacklogSequence = backlog.getPTSequence();
            backlog.setPTSequence(++BacklogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //priority
            if(projectTask.getPriority()==null || projectTask.getPriority() == 0){
                projectTask.setPriority(3);
            }
            //status
            if(projectTask.getStatus() == null || projectTask.getStatus()=="" ){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);




    }

    public Iterable<ProjectTask> findBacklogById(String id,String username) {
        projectService.findProjectByIdentifier(id,username);
        return  projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String sequence, String username){
        //make sure searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id,username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null)
            throw new  ProjectNotFoundException("Project Task with sequence: " + sequence + " doesn't exist");

        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new  ProjectNotFoundException("Project with ID:"+backlog_id+" has no project task with sequence: "+sequence);
        return projectTaskRepository.findByProjectSequence(sequence);

    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask ,String backlog_id ,String pt_id,String username){
        ProjectTask projectTask  = findPTByProjectSequence(updatedTask.getProjectIdentifier(),pt_id,username);
         projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence(String backlog_id , String pt_id,String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
