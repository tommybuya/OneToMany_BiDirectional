package com.oneToMany.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;

}
