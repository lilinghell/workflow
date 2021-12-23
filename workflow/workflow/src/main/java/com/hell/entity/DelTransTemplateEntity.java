package com.hell.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DelTransTemplateEntity {
    private String transName;
    @NotBlank
    private String transId;
    @NotNull
    private Integer entId;
}
