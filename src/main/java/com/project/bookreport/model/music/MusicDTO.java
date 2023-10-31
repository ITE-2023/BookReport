package com.project.bookreport.model.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {
    private String title;
    private String singer;
    private String imageUrl;
}
