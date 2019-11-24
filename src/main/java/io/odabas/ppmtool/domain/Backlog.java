package io.odabas.ppmtool.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    //TODO: oneToMany projectTasks belongs just one backlog   backlog could be one or more projectTasks

}
