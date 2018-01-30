package com.robot.humanrobot.model;

import com.robot.humanrobot.config.datastrategy.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamFloatValue;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="HUMANROBOT")
public class HumanRobot {

    @Id
    @SequenceGenerator(name = "human_generator", sequenceName = "human_sequence", allocationSize = 1)
    @GeneratedValue(generator = "human_generator")
    private Integer id;
    private String name;
    @PodamStrategyValue(CategorytStrategy.class)
    private String category;
    @PodamStrategyValue(StatusStrategy.class)
    private String status;
    @PodamStrategyValue(DateStrategy.class)
    private Date available_date;
    @PodamStrategyValue(DateStrategy.class)
    private Date produced_date ;
    @PodamDoubleValue(minValue = 0.12, maxValue = 10000.1568)
    private double price;
    @PodamStrategyValue(CurrencyStrategy.class)
    private String currency;
    @PodamStrategyValue(LocationStrategy.class)
    private String location;
    @PodamStrategyValue(IdProductStrategy.class)
    private String product_id;
    @PodamStringValue(strValue = "NO")
    private String quality_check;
    private Date build_date;
    private Date stock_date;
}
