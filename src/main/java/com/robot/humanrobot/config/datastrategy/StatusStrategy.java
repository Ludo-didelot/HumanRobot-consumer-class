package com.robot.humanrobot.config.datastrategy;

import com.mifmif.common.regex.Generex;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Ludovic on 21/01/2018.
 */
public class StatusStrategy implements AttributeStrategy<String> {

    @Override
    public String getValue(Class<?> aClass, List<Annotation> list) {
        Generex generex = new Generex("(PRODUCED|DELIVERED|BUILDING|SCHEDULED|INSTOCK)");

        // Generate random String
        String randomStr = generex.random();
        return randomStr;
    }
}


