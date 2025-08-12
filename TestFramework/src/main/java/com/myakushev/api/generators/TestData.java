package com.myakushev.api.generators;

import com.myakushev.api.models.Kanban;
import com.myakushev.api.models.KanbanDTO;
import com.myakushev.api.models.Task;
import com.myakushev.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private Task task;
    private KanbanDTO kanbanDTO;
    private Kanban kanban;

    public void delete() {
        var spec = Specifications.getSpec().authSpec();
        // ...
    }
}
