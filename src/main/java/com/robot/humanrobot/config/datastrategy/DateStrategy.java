package com.robot.humanrobot.config.datastrategy;

import com.mifmif.common.regex.Generex;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ludovic on 21/01/2018.
 */
public class DateStrategy implements AttributeStrategy<Date> {

    @Override
    public Date getValue(Class<?> aClass, List<Annotation> list) {
        Generex generex = new Generex("[2][0]([0][0-9]|[1][0-7])\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])");

        // Generate random String
        String randomStr = generex.random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = new Date();
        try {
             convertedCurrentDate = sdf.parse(randomStr);
        } catch (ParseException parse){
            parse.printStackTrace();
        }
        return convertedCurrentDate;
    }
}
