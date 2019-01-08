package com.robot.humanrobot.service;


import com.robot.humanrobot.model.HumanRobot;
import com.robot.humanrobot.repo.HumanRobotRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ludovic on 29/01/2018.
 */


@Service
@Getter
@Setter
@EnableAsync
public class HumanRobotService {

    @Autowired
    HumanRobotRepository humanRobotRepository;
    Boolean generationEnded = false;
    Map<String,Boolean> stopGeneration = new HashMap();
    Map<String,Boolean> stopUpdate = new HashMap();

    public List<HumanRobot> getAllHumanRobots() {
        return humanRobotRepository.findAll();
    }
    public HumanRobot getHumanRobot(@PathVariable("id") Integer id) {
        return humanRobotRepository.findOne(id);
    }
    @Async
    public void generateData(int numberOfData, String creator) {
        if (numberOfData>10000)
            numberOfData=10000;
        for (int i = 0; i < numberOfData; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            HumanRobot humanRobot = factory.manufacturePojo(HumanRobot.class);
            humanRobot.setProduct_id("HumanRobot" + humanRobot.getCategory() + humanRobot.getProduct_id())
                    .setName("IRobot-" + humanRobot.getName())
                    .setTechRawCreationDate(new Date())
                    .setCreator(creator);
            humanRobotRepository.saveAndFlush(humanRobot);
            System.out.println("generateData - Current thread "+ Thread.currentThread().getId()+" for - "+creator);
            try {
                Thread.sleep((ThreadLocalRandom.current().nextInt(10)) * 200);
            } catch (InterruptedException iter) {
                throw new RuntimeException(iter);
            }
            if (stopGeneration!=null && stopGeneration.containsKey(creator)) {
                System.out.println("generateData - Current thread " + Thread.currentThread().getId() + " for - " + creator + " is stopping !!!!");
                break;
            }
        }
        getStopGeneration().remove(creator);
        System.out.println("generateData - Current thread " + Thread.currentThread().getId() + " for - " + creator + " stopped !!!!");
    }
    @Async
    public void modifyGeneratedData (String creator, int time){
            Date startDate = new Date();
            if(time>3600*1000)
                time=3600*1000;
            while(new Date().getTime()<startDate.getTime()+time) {
                List<HumanRobot> list = humanRobotRepository.findAllByStatusAndCreator("BUILDING",creator);
                list.forEach(humanRobot -> {
                    humanRobot.setStatus("PRODUCED");
                    humanRobot.setProduced_date(new Date());
                    humanRobotRepository.saveAndFlush(humanRobot);
                });

                List<HumanRobot> list2 = humanRobotRepository.findAllByStatusAndCreator("SCHEDULED",creator);
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
                List<HumanRobot> list3 = humanRobotRepository.findAllByStatusAndCreator("PRODUCED",creator);
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
                if(stopUpdate!=null && stopUpdate.containsKey(creator)){
                    System.out.println("modifyGeneratedData - Current thread "+ Thread.currentThread().getId()+" for - "+creator+" is stopping !!!");
                    break;
                }
                System.out.println("modifyGeneratedData - Current thread "+ Thread.currentThread().getId()+" for - "+creator);
           }
        System.out.println("modifyGeneratedData - Current thread "+ Thread.currentThread().getId()+" for - "+creator+" Stopped !!!");
        getStopGeneration().remove(creator);
    }

    public void deleteAll(String creator) {
        List<HumanRobot> listToDelete = humanRobotRepository.findAllByCreator(creator);
        listToDelete.forEach(humanRobot -> {
            humanRobotRepository.delete(humanRobot);
        });

    }

    public void appendStopGeneration(String creator) {
        getStopGeneration().putIfAbsent(creator,true);
    }
    public void appendStopUpdate(String creator) {
        getStopUpdate().putIfAbsent(creator,true);
    }
    public void appendStopAll(String creator) {
        getStopGeneration().putIfAbsent(creator,true);
        getStopUpdate().putIfAbsent(creator,true);
    }
}
