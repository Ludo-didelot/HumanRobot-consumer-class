package com.robot.humanrobot.controller;

import com.robot.humanrobot.consumer.ConsumerKafka;
import com.robot.humanrobot.model.HumanRobot;
import com.robot.humanrobot.service.HumanRobotService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HumanRobotController {

    @Autowired
    HumanRobotService humanRobotService;
    @Autowired
    ConsumerKafka consumer;
    @Autowired
    Environment env;

    @RequestMapping(value="/admin/startconsumer", method=RequestMethod.GET)
    public ResponseEntity<String> startConsumer() {
        consumer.start();
        return new ResponseEntity<String>("Consumer status :"+consumer.isStart(), HttpStatus.OK);
    }
    @RequestMapping(value="/admin/stopconsumer", method=RequestMethod.GET)
    public ResponseEntity<String> stopConsumer() {
        consumer.stop();
        return new ResponseEntity<String>("Consumer status :"+consumer.isStart(), HttpStatus.OK);
    }
    @RequestMapping(value="/admin/status", method=RequestMethod.GET)
    public ResponseEntity<String> status() {
        return new ResponseEntity<String>("Consumer status :"+consumer.isStart(), HttpStatus.OK);
    }

    @RequestMapping(value="/producer", method= RequestMethod.GET)
    public String getResult(@RequestParam("topic") String topic,@RequestParam("input") String value) {
        return env.getProperty("message.response");
    }

    @RequestMapping(value="/humanrobots", method=RequestMethod.GET)
    public List<HumanRobot> getAllHumanRobots() {

        return humanRobotService.getAllHumanRobots();
    }
    @RequestMapping(value="/ResetDatabase", method=RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@RequestParam(value = "creator", required = true) String creator) {
        this.humanRobotService.deleteAll(creator);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    @Timed(
            value = "get.human.robot.stat",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"mytag", "customhuman"}
    )
    @RequestMapping(value="/humanrobot/{id}", method=RequestMethod.GET)
    public HumanRobot getHumanRobot(@PathVariable("id") Integer id) {
        HumanRobot result = humanRobotService.getHumanRobot(id);
        ArrayList<Tag> array  = new ArrayList();
        array.add(Tag.of("my tag","my value"));
        array.add(Tag.of("my tag2","my value2"));
        Metrics.counter("search.robot", array).increment();
        return result;
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
