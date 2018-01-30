package com.robot.humanrobot.repo;

import com.robot.humanrobot.model.HumanRobot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HumanRobotRepository extends JpaRepository<HumanRobot, Integer>{

    List<HumanRobot> findByStatus(String status);
}
