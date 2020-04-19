package io.odabas.ppmtool.web;

import io.odabas.ppmtool.domain.Project;
import io.odabas.ppmtool.domain.ProjectTask;
import io.odabas.ppmtool.services.MapValidationErrorService;
import io.odabas.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.security.Principal;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project , BindingResult result, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return  errorMap;

        Project projectObj = projectService.saveOrUpdateProject(project,principal.getName());
        return new ResponseEntity<Project>(projectObj, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectFromIdentifier( @PathVariable String projectId){
        Project projectObjFind = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(projectObjFind,HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?>deleteProjectFromIdentifier(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId.toUpperCase());
        return new ResponseEntity<String>("ProjectId '"+projectId+"' was deleted succesfully",HttpStatus.OK);
    }



}
