package com.robot.humanrobot.service;

import com.robot.humanrobot.model.HumanRobot;
import com.robot.humanrobot.repo.HumanRobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ludovic on 29/01/2018.
 */


@Service
public class HumanRobotService {
    @Autowired
    HumanRobotRepository humanRobotRepository;

    public List<HumanRobot> getAllHumanRobots() {
        return humanRobotRepository.findAll();
    }
    public HumanRobot getHumanRobot(@PathVariable("id") Integer id) {
        return humanRobotRepository.findOne(id);
    }
    public void generateData() {
        Runnable r2 = () -> {
            for (int i=0;i<2000;i++) {
                PodamFactory factory = new PodamFactoryImpl();
                HumanRobot humanRobot = factory.manufacturePojo(HumanRobot.class);
                humanRobot.setProduct_id("HumanRobot" + humanRobot.getCategory() + humanRobot.getProduct_id());
                humanRobot.setName("IRobot-" + humanRobot.getName());
                humanRobotRepository.saveAndFlush(humanRobot);
                try{
                    Thread.sleep((ThreadLocalRandom.current().nextInt(10))*200);
                }
                catch (InterruptedException iter)
                {
                        throw new RuntimeException(iter);
                }
            }
        };
        Thread th2 = new Thread(r2);
        th2.start();
        Runnable r3 = () -> {
            Date startDate = new Date();
            while(new Date().getTime()<startDate.getTime()+3600*1000) {
                List<HumanRobot> list = humanRobotRepository.findByStatus("BUILDING");
                list.forEach(humanRobot -> {
                    humanRobot.setStatus("PRODUCED");
                    humanRobot.setProduced_date(new Date());
                    humanRobotRepository.saveAndFlush(humanRobot);
                });

                List<HumanRobot> list2 = humanRobotRepository.findByStatus("SCHEDULED");
                list2.forEach(humanRobot -> {
                    humanRobot.setStatus("BUILDING");
                    humanRobot.setBuild_date(new Date());
                    humanRobotRepository.saveAndFlush(humanRobot);
                });
                try{
                    Thread.sleep((ThreadLocalRandom.current().nextInt(10))*200);
                }
                catch (InterruptedException iter)
                {
                    throw new RuntimeException(iter);
                }
                List<HumanRobot> list3 = humanRobotRepository.findByStatus("PRODUCED");
                list3.forEach(humanRobot -> {
                    humanRobot.setStatus("INSTOCK");
                    humanRobot.setStock_date(new Date());
                    humanRobot.setQuality_check("OK");
                    if (!humanRobot.getProduct_id().contains("1"))
                        humanRobot.setQuality_check("KO");
                    humanRobotRepository.saveAndFlush(humanRobot);
                });
                try{
                    Thread.sleep((ThreadLocalRandom.current().nextInt(10))*200);
                }
                catch (InterruptedException iter)
                {
                    throw new RuntimeException(iter);
                }
           }
        };
        Thread th3 = new Thread(r3);
        th3.start();
    }

    public void deleteAll() {
        humanRobotRepository.deleteAll();
    }
}
