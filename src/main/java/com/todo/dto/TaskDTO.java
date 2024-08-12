package com.todo.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String content;
    private boolean completed;
}
