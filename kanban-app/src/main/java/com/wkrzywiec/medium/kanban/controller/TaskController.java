package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.model.TaskDTO;
import com.wkrzywiec.medium.kanban.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Task Management", description = "Endpoints for managing tasks")
public class TaskController {

    private final TaskService taskService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.service.url:http://localhost:9999}")
    private String externalServiceUrl;

    @GetMapping("/")
    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllTasks(){
        try {
            return new ResponseEntity<>(
                    taskService.getAllTasks(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Returns a single task by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found task",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getTask(@PathVariable Long id){
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        optTask.get(),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("")
    @Operation(summary = "Get task by title", description = "Returns a task by its title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found task",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getTaskByTitle(@RequestParam String title){
        try {
            Optional<Task> optTask = taskService.getTaskByTitle(title);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        optTask.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No task found with a title: " + title, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/")
    @Operation(summary = "Create new task", description = "Creates and saves a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO){
        try {
            return new ResponseEntity<>(
                    taskService.saveNewTask(taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Updates an existing task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        taskService.updateTask(optTask.get(), taskDTO),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task", description = "Deletes a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                taskService.deleteTask(optTask.get());
                return new ResponseEntity<>(String.format("Task with id: %d was deleted", id), HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/{id}/promote-to-system")
    @Operation(summary = "Promote task to system task", description = "Promotes a regular task to system task and sends it to external service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully promoted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Task is already a system task"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> promoteToSystemTask(@PathVariable Long id){
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isEmpty()) {
                return noTaskFoundResponse(id);
            }

            Task task = optTask.get();

            if (task.isSystemTask()) {
                return new ResponseEntity<>("Task is already a system task", HttpStatus.BAD_REQUEST);
            }

            ExternalTaskRequest request = new ExternalTaskRequest();
            request.setId(String.valueOf(task.getId()));
            request.setTitle(task.getTitle());
            request.setDescription(task.getDescription() != null ? task.getDescription() : "");
            request.setStatus(task.getStatus() != null ? task.getStatus().toString() : "TODO");

            String wiremockUrl = externalServiceUrl + "/external/send-task";
            ExternalTaskResponse response = restTemplate.postForObject(wiremockUrl, request, ExternalTaskResponse.class);

            if (response == null || response.getExternalId() == null) {
                return new ResponseEntity<>("External service returned invalid response", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Task updatedTask = taskService.promoteToSystemTask(id, response.getExternalId());

            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        } catch (RestClientException e) {
            return new ResponseEntity<>("Failed to send task to external system: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/load-system-tasks")
    @Operation(summary = "Load system tasks", description = "Loads system tasks from external service and adds them to the board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "System tasks successfully loaded",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> loadSystemTasks(@RequestParam(defaultValue = "3") int limit){
        try {
            String wiremockUrl = externalServiceUrl + "/external/system-tasks?limit=" + limit;
            ExternalSystemTask[] systemTasks = restTemplate.getForObject(wiremockUrl, ExternalSystemTask[].class);

            if (systemTasks == null) {
                return new ResponseEntity<>("No system tasks received from external service", HttpStatus.OK);
            }

            for (ExternalSystemTask sysTask : systemTasks) {
                taskService.saveSystemTask(
                        sysTask.getTitle(),
                        sysTask.getDescription(),
                        String.valueOf(sysTask.getId())
                );
            }

            return new ResponseEntity<>("Successfully loaded " + systemTasks.length + " system tasks", HttpStatus.OK);

        } catch (RestClientException e) {
            return new ResponseEntity<>("Failed to load system tasks: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noTaskFoundResponse(Long id){
        return new ResponseEntity<>("No task found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @Data
    @Schema(description = "Request object for external task promotion")
    public static class ExternalTaskRequest {
        @Schema(description = "Task ID", example = "123")
        private String id;
        @Schema(description = "Task title", example = "Implement login feature")
        private String title;
        @Schema(description = "Task description", example = "Add OAuth2 authentication")
        private String description;
        @Schema(description = "Task status", example = "TODO")
        private String status;
    }

    @Data
    @Schema(description = "Response object from external service")
    public static class ExternalTaskResponse {
        @Schema(description = "External system ID", example = "ext_12345")
        private String externalId;
        @Schema(description = "Response status", example = "SUCCESS")
        private String status;
        @Schema(description = "Response message", example = "Task received")
        private String message;
        @Schema(description = "Timestamp when task was received", example = "2026-05-13T09:00:00")
        private LocalDateTime receivedAt;
    }

    @Data
    @Schema(description = "System task object from external service")
    public static class ExternalSystemTask {
        @Schema(description = "External task ID", example = "1")
        private int id;
        @Schema(description = "Task title", example = "System maintenance")
        private String title;
        @Schema(description = "Task description", example = "Run system backup")
        private String description;
        @Schema(description = "Task priority", example = "HIGH")
        private String priority;
    }
}