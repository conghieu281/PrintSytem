package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EPrintJob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrintJobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long designId;

    @Enumerated(EnumType.STRING)
    private EPrintJob printJobStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "printJobs", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ResourceForPrintJob> resourceForPrintJobs ;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_printJob-design"))
    private Design designPrintJob;

    public PrintJobs(Long id) {
        this.id = id;
    }
}
