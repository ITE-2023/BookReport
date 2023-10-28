package com.project.bookreport.domain.status;

public enum EmotionType {
    HAPPY("행복"), SCARY("공포"), SURPRISED("놀람"), SAD("슬픔"), ANGER("분노");

    private final String msg;

    EmotionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
