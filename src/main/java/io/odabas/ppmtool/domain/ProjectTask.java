package io.odabas.ppmtool.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.GenericArrayType;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(updatable = false)
    private String prokjectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCrieteria;
    private  String status;
    private Integer priority;
    private Date dueDate;
    //TODO:ManyToOne Backlog

    @Column(updatable = false)
    private String projectIdentifier;

    private Date created_At;
    private Date update_At;


    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }



}
