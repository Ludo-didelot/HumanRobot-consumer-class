package com.robot.humanrobot.controller;

import com.robot.humanrobot.model.HumanRobot;
import com.robot.humanrobot.service.HumanRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> delete(@RequestParam(value = "creator", required = true) String creator) {
        this.humanRobotService.deleteAll(creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value="/humanrobot/{id}", method=RequestMethod.GET)
    public HumanRobot getHumanRobot(@PathVariable("id") Integer id) {
        return humanRobotService.getHumanRobot(id);
    }

    @RequestMapping(value="/startFullDataProcess", method=RequestMethod.GET)
    public ResponseEntity<Boolean> startFullDataProcess(@RequestParam(value = "creator", required = true) String creator,
                                                @RequestParam(value = "robotnum", required = true) int numberOfData ,
                                                @RequestParam(value = "updateDataTime", required = true) int secondInTimeToUpdate ) {
        humanRobotService.generateData(numberOfData, creator);
        humanRobotService.modifyGeneratedData(creator,secondInTimeToUpdate);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @RequestMapping(value="/startDataGeneration", method=RequestMethod.GET)
    public ResponseEntity<Boolean> generateData(@RequestParam(value = "creator", required = true) String creator,
                                                @RequestParam(value = "robotnum", required = true) int numberOfData ) {
        humanRobotService.generateData(numberOfData, creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @RequestMapping(value="/startDataUpdate", method=RequestMethod.GET)
    public ResponseEntity<Boolean> startFullDataProcess(@RequestParam(value = "creator", required = true) String creator,
                                                        @RequestParam(value = "updateDataTime", required = true) int secondInTimeToUpdate ) {
        humanRobotService.modifyGeneratedData(creator,secondInTimeToUpdate);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @RequestMapping(value="/stopGeneration", method=RequestMethod.GET)
    public ResponseEntity<Boolean> stopGeneration(@RequestParam(value = "creator", required = true) String creator) {
        humanRobotService.appendStopGeneration(creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @RequestMapping(value="/stopUpdate", method=RequestMethod.GET)
    public ResponseEntity<Boolean> stopUpdate(@RequestParam(value = "creator", required = true) String creator) {
        humanRobotService.appendStopUpdate(creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @RequestMapping(value="/stopAll", method=RequestMethod.GET)
    public ResponseEntity<Boolean> stopAll(@RequestParam(value = "creator", required = true) String creator) {
        humanRobotService.appendStopAll(creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
