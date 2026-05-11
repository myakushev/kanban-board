package api;

import lombok.Data;

import java.util.List;

@Data
public class KanbanCreateRes {
    private Integer id;
    private String title;
    private List<TaskRes> taskRes;
}
