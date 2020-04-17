package io.odabas.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(updatable = true, unique =true)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private  String status;
    private Integer priority;
    private Date dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id",updatable = false,nullable = false)
    @JsonIgnore
    private Backlog backlog;

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


    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", backlog=" + backlog +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", created_At=" + created_At +
                ", update_At=" + update_At +
                '}';
    }
}


