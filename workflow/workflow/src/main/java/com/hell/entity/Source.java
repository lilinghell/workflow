package com.hell.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Source implements Serializable {
    private Long id;//nodeId
    private String position;//right、left、top、bottom
}
