package com.hexin.demo.mode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author hex1n
 * @Date 2022/2/22 14:55
 * @Description
 */
@AllArgsConstructor
@Getter
public enum TypeEnum {

    Date(1,"dateService",""),
    Enum(2,"dateService",""),
    Number(3,"dateService","")
    ;


    private int type;
    private String clazz;
    private String desc;

    public String getClazzByType(int type){

        String clazz = Stream.of(TypeEnum.values()).filter(typeEnum -> Objects.equals(type, typeEnum.type)).findFirst().map(typeEnum -> typeEnum.clazz).orElse("");

        return clazz;
    }
}
