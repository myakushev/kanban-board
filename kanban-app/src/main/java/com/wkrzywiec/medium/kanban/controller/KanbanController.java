package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.model.KanbanDTO;
import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.model.TaskDTO;
import com.wkrzywiec.medium.kanban.service.KanbanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/kanbans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Kanban Board Management", description = "Endpoints for managing kanban boards")
public class KanbanController {

    private final KanbanService kanbanService;

    @GetMapping("/")
    @Operation(summary = "Get all kanban boards", description = "Returns a list of all kanban boards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved kanban boards",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllKanbans(){
        try {
            return new ResponseEntity<>(
                    kanbanService.getAllKanbanBoards(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get kanban board by ID", description = "Returns a single kanban board by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found kanban board",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "404", description = "Kanban board not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getKanban(@PathVariable Long id){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get(),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("")
    @Operation(summary = "Get kanban board by title", description = "Returns a kanban board by its title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found kanban board",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "404", description = "Kanban board not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getKanbanByTitle(@RequestParam String title){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanByTitle(title);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No kanban found with a title: " + title, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/")
    @Operation(summary = "Create new kanban board", description = "Creates and saves a new kanban board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kanban board successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createKanban(@RequestBody KanbanDTO kanbanDTO){
        try {
            return new ResponseEntity<>(
                    kanbanService.saveNewKanban(kanbanDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update kanban board", description = "Updates an existing kanban board by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kanban board successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "404", description = "Kanban board not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> updateKanban(@PathVariable Long id, @RequestBody KanbanDTO kanbanDTO){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        kanbanService.updateKanban(optKanban.get(), kanbanDTO),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete kanban board", description = "Deletes a kanban board by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kanban board successfully deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Kanban board not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteKanban(@PathVariable Long id){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                kanbanService.deleteKanban(optKanban.get());
                return new ResponseEntity<>(
                        String.format("Kanban with id: %d was deleted", id),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{kanbanId}/tasks/")
    @Operation(summary = "Get all tasks in kanban board", description = "Returns a list of all tasks for a specific kanban board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Kanban board not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllTasksInKanban(@PathVariable Long kanbanId){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(kanbanId);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get().getTasks(),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(kanbanId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/{kanbanId}/tasks/")
    @Operation(summary = "Create task and assign to kanban board", description = "Creates a new task and assigns it to the specified kanban board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created and assigned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kanban.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createTaskAssignedToKanban(@PathVariable Long kanbanId, @RequestBody TaskDTO taskDTO){
        try {
            return new ResponseEntity<>(
                    kanbanService.addNewTaskToKanban(kanbanId, taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noKanbanFoundResponse(Long id){
        return new ResponseEntity<>("No kanban found with id: " + id, HttpStatus.NOT_FOUND);
    }
}