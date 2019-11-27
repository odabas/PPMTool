package io.odabas.ppmtool.exceptions;

import io.odabas.ppmtool.domain.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundExceptionResponse {
    private String projectNotFound;

    public ProjectNotFoundExceptionResponse(String projectNotFound){
        this.projectNotFound = projectNotFound;
    }

}
