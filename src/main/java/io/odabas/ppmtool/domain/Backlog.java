package io.odabas.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Backlog  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    //TODO: oneToOne Each proj has one backlog  <->  each backlog has one proj
    @OneToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "project_id",nullable = false)
    @JsonIgnore // JsonIgnore breaks infinite JSON recursion
    private Project project;

    //TODO: oneToMany projectTasks belongs just one backlog  <->  backlog could be one or more projectTasks

    @OneToMany( cascade = CascadeType.ALL ,fetch = FetchType.EAGER,mappedBy = "backlog")
    private List<ProjectTask> projectTask = new ArrayList<>();

}
