package com.myakushev.execution.requests.webapi;

import com.myakushev.execution.HttpBaseClient;
import com.myakushev.execution.RequestApi;
import com.myakushev.execution.requests.Requests;
import io.restassured.response.Response;

public class KanbanBoardApi extends RequestApi {

    public KanbanBoardApi(HttpBaseClient httpBaseClient) {
        super(httpBaseClient);
    }

    public Response createKanbanBoard(String body) {
        return sendPostRequest(Requests.KanbanBoard.CREATE_KANBAN_BOARD, body);
    }
}
