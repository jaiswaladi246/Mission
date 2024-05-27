package ca.sheridancollege;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestController {

	private DatabaseAccess da;
	private MockMvc mockMvc;
	
	@Autowired
	public void setDa(DatabaseAccess da) {
		this.da = da;
	}

	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
		
	@Test
	public void testRoot() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}

	@Test
	public void testUpdateMission() throws Exception {
		
		List<Mission> missions = da.getMissions("Johnny English");
				
		Mission mission = missions.get(0);
		Long id = mission.getId();		System.out.println(id);
		mission.setTitle("Rescue the world");
		
		mockMvc.perform(post("/updateMission")
			.flashAttr("mission", mission))
			.andExpect(status().isOk())
			.andExpect(view().name("view_mission"));
		
		mission = da.getMission(id);
		// pass if the title equals to what the user tested
		assertEquals(mission.getTitle(), "Rescue the world");
	}

	@Test
	public void testAddMission() throws Exception{
		mockMvc.perform(get("/addMission"))
				.andExpect(status().isOk())
				.andExpect(view().name("create_mission"))
				.andDo(print());
	}

	@Test
	public void testDeleteMission() throws Exception {
		// retrieve the mission list and the fields
		List<Mission> missions = da.getMissions("Johnny English");
		Mission mission = missions.get(0);		
		Long id = mission.getId();	
		String agent = mission.getAgent();	
		
		int origSize = da.getMissions(agent).size();
		
		// execute the test
		mockMvc.perform(get("/deleteMission/{id}", id))
			.andExpect(status().isOk())
			.andExpect(view().name("view_mission"))
			.andDo(print());
		
		int returnValue = da.deleteMission(id);
				
		int newSize = da.getMissions(agent).size();
		// pass if the new size is 1 smaller than the original size
		assertEquals(newSize, origSize - 1);
	}

	@Test
	public void testViewMissions() throws Exception {
		// use LinkedMultipleValueMap to mimic request parameters
		LinkedMultiValueMap<String, String> requestParams 
				= new LinkedMultiValueMap<>();
		// add the values
		requestParams.add("agent", "Johnny English");
		// test the request
		mockMvc.perform(get("/viewMissions").params(requestParams))
			.andExpect(status().isOk())
			.andExpect(view().name("view_mission"))
			.andDo(print());
	}

	@Test
	public void testCreateMission() throws Exception {
		// retrieve the mission list and the fields
		List<Mission> missions = da.getMissions("Johnny English");
		Mission mission = missions.get(0);
		String agent = mission.getAgent();	
		
		int origSize = da.getMissions(agent).size();
		
		// execute the test
		mockMvc.perform(post("/createMission")
			.flashAttr("mission", mission))
			.andExpect(status().isOk())
			.andExpect(view().name("view_mission"))
			.andDo(print());
		int newSize = da.getMissions(agent).size();
		// pass if the new size is 1 bigger than the original size
		assertEquals(newSize, origSize + 1);
	}
	
	@Test
	public void testEditMission() throws Exception {
		// retrieve the mission list and the fields
		List<Mission> missions = da.getMissions("Johnny English");
		Mission mission = missions.get(0);		
		Long id = mission.getId();	
		
		// perform the test
		mockMvc.perform(get("/editMission/{id}", id))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_mission"))
				.andDo(print());	
	}
	
	@Test
	public void testUpdateSession() throws Exception {
		// the method that assists other methods and doesn't have mapping
	}
}
