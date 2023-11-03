package com.project.bookreport.model.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReportRequest {
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private String emotion;
}
