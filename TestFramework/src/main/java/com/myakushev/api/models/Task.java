package com.myakushev.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myakushev.api.enums.Color;
import com.myakushev.api.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    String id;
    String title;
    String description;
    Color color;
    TaskStatus status;
}
