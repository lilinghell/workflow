package com.hell.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Connection {
    @NotNull
    private Long id;//
    @NotBlank
    private String type;//类型
    private Source source;//源
    private Source destination;//目标
}
