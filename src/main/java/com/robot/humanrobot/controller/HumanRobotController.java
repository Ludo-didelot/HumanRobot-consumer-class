package com.robot.humanrobot.controller;

import com.robot.humanrobot.model.HumanRobot;
import com.robot.humanrobot.repo.HumanRobotRepository;
import com.robot.humanrobot.service.HumanRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamUtils;

import java.util.List;

@RestController
public class HumanRobotController {

    @Autowired
    HumanRobotService humanRobotService;

    @RequestMapping(value="/humanrobots", method=RequestMethod.GET)
    public List<HumanRobot> getAllHumanRobots() {

        return humanRobotService.getAllHumanRobots();
    }
    @RequestMapping(value="/ResetDatabase", method=RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete() {
        this.humanRobotService.deleteAll();
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value="/humanrobot/{id}", method=RequestMethod.GET)
    public HumanRobot getHumanRobot(@PathVariable("id") Integer id) {
        return humanRobotService.getHumanRobot(id);
    }

    @RequestMapping(value="/generateData", method=RequestMethod.GET)
    public ResponseEntity<Boolean> generateData() {
        humanRobotService.generateData();
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

}
