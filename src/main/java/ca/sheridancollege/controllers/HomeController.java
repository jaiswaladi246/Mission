package ca.sheridancollege.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.database.DatabaseAccess;

/**
 * This is the controller that defines each of the pages.
 * Some methods utilize the session.
 * This program is made for Assignment 3 in Java 3 class.
 * @author Mihye 
 */
@Controller
public class HomeController {

	private DatabaseAccess database;

	@Autowired
	public HomeController(DatabaseAccess database) {
		this.database = database;
	}
	
	@GetMapping("/viewMissions")
	public String viewMissions(@RequestParam String agent, 
			HttpSession session) {		
		updateSession(agent, session);
		return "view_mission";
	}
	
	@GetMapping("/addMission")
	public String addMission(Model model) {
		model.addAttribute("mission", new Mission());	
		return "create_mission";
	}	
	
	@PostMapping("/createMission")
	public String createMission(@ModelAttribute Mission mission, 
			HttpSession session) {
		int returnValue = database.addMission(mission);
		updateSession(mission.getAgent(), session);
		return "view_mission";
	}
	
	@GetMapping("/editMission/{id}")
	public String editMission(@PathVariable Long id, Model model) {
		// make a mission that has the id accepted as parameter
		Mission mission = database.getMission(id);
		// attach attribute that is mission
		model.addAttribute("mission", mission);
		return "edit_mission";
	}
	
	@GetMapping("/deleteMission/{id}")
	public String deleteMission(@PathVariable Long id, HttpSession session) {
		Mission mission = database.getMission(id);
		String agent = mission.getAgent();
		
		int returnValue = database.deleteMission(id);
		updateSession(agent, session);
		
		return "view_mission";		
	}
	

	@PostMapping("/updateMission")
	public String updateMission(@ModelAttribute Mission mission, 
			Model model, HttpSession session) {
		System.out.println(mission);
		
		int returnValue = database.updateMission(mission);
		
		updateSession(mission.getAgent(), session);
		
		return "view_mission";
	}
	
	private void updateSession(String agent, HttpSession session) {		
		
		List<Mission> missions = database.getMissions(agent);
		
		session.setAttribute("agent", agent);
		session.setAttribute("missionList", missions);
		
	}	
}
