package api;

import lombok.Data;

@Data
public class TaskRes {
    private Integer id;
    private String title;
    private String description;
    private String color;
    private String status;
}
