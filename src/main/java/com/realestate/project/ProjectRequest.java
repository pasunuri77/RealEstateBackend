package com.realestate.project;

import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {
    private String name;

    private String description;

    private String location;

    private ProjectType projectType;

    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate expectedCompletionDate;

    private LocalDate completedDate;

}
