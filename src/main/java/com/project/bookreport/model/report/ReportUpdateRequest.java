package com.project.bookreport.model.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReportUpdateRequest {
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;
    private String content;
}