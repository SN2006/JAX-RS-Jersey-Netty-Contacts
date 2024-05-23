package com.example.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    NO_CONTENT("\"No Content.\""),
    NOT_FOUND("\"Not Found.\""),
    BAD_REQUEST("\"Bad Request\""),
    DELETED("\"Deleted.\"");

    private final String resMsg;
}
