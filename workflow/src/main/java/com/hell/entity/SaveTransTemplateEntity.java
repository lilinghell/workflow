package com.hell.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveTransTemplateEntity {
    @NotBlank
    private String transName;
    @NotBlank
    private String transId;
    @NotBlank
    private String apiPath;
    @NotNull
    private Integer templateId;
    @NotNull
    private Integer entId;
}
