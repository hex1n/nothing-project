package com.hexin.demo.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hex1n
 * @date 2021/1/13 9:41
 * @description
 */
@Slf4j
public class StringTest {

    public static void main(String[] args) throws ParseException {
        String createTime="2021-08-07 15:31:24";
        DateFormat formatCreatePara = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat strFormat = new SimpleDateFormat("MM-dd E HH MM");
        Date dateCreatePara = formatCreatePara.parse(createTime);
        String labelCreatePara = strFormat.format(dateCreatePara);
        String[] labelCreateList = labelCreatePara.split(" ");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCreatePara);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        log.info("labelCreatePara========:{}",labelCreatePara);
        log.info("week========:{}",week);
        log.info("lableCreateList:{}", JSON.toJSONString(labelCreateList));
    }


}
