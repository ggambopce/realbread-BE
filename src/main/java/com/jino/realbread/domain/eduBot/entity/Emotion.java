package com.jino.realbread.domain.eduBot.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Emotion {
    REJECT("거부"),
    THINKING("고민중"),
    ANGRY_DISAGREE("그건아니지화남"),
    SORRY_POSITIVE("긍적긍적미안"),
    EXCITED("기대만발"),
    BASIC_SMILE("기본스마일"),
    SENSE_MAN("나센스있는데"),
    ANNOYED("나짜증"),
    DUMBFOUNDED("나황당"),
    AWKWARD("난감한표정"),
    VERY_SORRY("너무미안"),
    LOVE_HAPPY("너무좋아사랑"),
    SURPRISED("놀래키기"),
    CRUSH("누군가를좋아함"),
    SWEET_YUMMY("달콤하고맛있어"),
    SHAKING("덜덜무서움"),
    THUMBS_UP("따봉좋아"),
    SWEAT_EMBARRASSED("땀삐질당황"),
    SMART("똑똑이"),
    SORRY("미안"),
    WISH("바램"),
    EXCITED_BLOW("뿌뿌신난다"),
    SAD_POUT("삐짐슬픔"),
    LOVE_HEART("사랑고백하트"),
    BIRTHDAY("생일축하"),
    FINGER_BLAME("손가락비난"),
    SHY_HELLO("수줍은안녕"),
    SHY_SMILE("수줍은웃음"),
    SHY_HEART("수줍은하트"),
    SHY_LOVE_HEBUL("수줍은헤벌레사랑"),
    HATE("싫어"),
    SERIOUS("심각한고민"),
    ANGRY_ARGUE("싸우고싶은화남"),
    FIGHT("싸우자"),
    HELLO("안녕"),
    CURIOUS("오궁금"),
    START_COOK("요리시작"),
    COOKING("요리중"),
    DENY_BLAME("우거절비난"),
    PROUD("우쭐나최고"),
    DANGER_STOP("위험해멈춰"),
    TEMPTING("유혹"),
    WORRIED("으악걱정돼"),
    NARCISSISM("자아도취"),
    SLEEPY("졸림"),
    GOOD_IDEA("좋은아이디어"),
    FOCUS("집중해"),
    SEARCHING("찾는중"),
    GIGGLE("키득키득"),
    DRINK("한잔해"),
    LOVE_HEBUL("헤벌레사랑에빠짐"),
    LISTEN_ANGRY("화나는데들어봄"),
    SAD_CRIES("훌쩍슬픔");

    private final String label;

    Emotion(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Emotion fromLabel(String label) {
        for (Emotion e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return Emotion.BASIC_SMILE;
    }

}
