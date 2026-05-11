package api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskReq {
    private String title;
    private String description;
    private String color;
    private String status;
}
