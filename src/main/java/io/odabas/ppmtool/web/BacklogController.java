package io.odabas.ppmtool.web;

import io.odabas.ppmtool.domain.ProjectTask;
import io.odabas.ppmtool.services.MapValidationErrorService;
import io.odabas.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService maplValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){

        ResponseEntity<?> errorMap = maplValidationErrorService.MapValidationService(result);
        if (errorMap !=null ) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);


    }
}
