package io.odabas.ppmtool.web;

import io.odabas.ppmtool.domain.Project;
import io.odabas.ppmtool.services.MapValidationErrorService;
import io.odabas.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project , BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return  errorMap;

        Project projectObj = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(projectObj, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectFromIdentifier( @PathVariable String projectId){
        Project projectObjFind = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(projectObjFind,HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?>deleteProjectFromIdentifier(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId.toUpperCase());
        return new ResponseEntity<String>("ProjectId '"+projectId+"' was deleted succesfully",HttpStatus.OK);
    }
}
