package io.odabas.ppmtool.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.odabas.ppmtool.domain.Backlog;
import io.odabas.ppmtool.domain.ProjectTask;
import io.odabas.ppmtool.repositories.BacklogRepository;
import io.odabas.ppmtool.repositories.ProjectTaskRepository;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService  {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSequence();
        backlog.setPTSequence(++BacklogSequence);
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //priority
        if(projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }
        //status
        if(projectTask.getStatus()=="" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);

    }

}
