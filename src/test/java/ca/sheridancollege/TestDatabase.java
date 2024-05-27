package ca.sheridancollege;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.database.DatabaseAccess;

/**
 * This class is to test the methods in DatabaseAccess.
 * 
 * @author Mihye
 */
@SpringBootTest
class TestDatabase {

	private DatabaseAccess da;
	
	@Autowired
	public void setDa(DatabaseAccess da) {
		this.da = da;
	}

	@Test
	public void testDatabaseAddMission() {
		// it tests add functionality on Database directly
		Mission mission = new Mission();
		mission.setAgent("Starlord");
		mission.setGadget1("The element gun");
		mission.setGadget2("Sub-machine gun");
		mission.setTitle("Peter Quill");

		// get the original number of Missions
		int origSize = da.getMissions("Starlord").size();

		da.addMission(mission);
		// the size after adding a mission
		int newSize = da.getMissions("Starlord").size();

		// it will pass if the new size is 1 bigger than the original
		assertEquals(newSize, origSize + 1);
	}

	@Test
	public void testDatabaseDeteteMission() {
		// retrieves the mission
		List<Mission> missions = da.getMissions("Johnny English");

		// get an id
		Long id = missions.get(0).getId();

		// get the size of he original missions
		int origSize = da.getMissions(missions.get(0).getAgent()).size();

		da.deleteMission(id);
		// the size after deleting a mission
		int newSize = da.getMissions(missions.get(0).getAgent()).size();

		// it will pass if the new size is 1 smaller than the original
		assertEquals(newSize, origSize - 1);
	}

	@Test
	public void testDatabaseUpdateMission() {
		// retrieves the mission and fields
		List<Mission> missions = da.getMissions("Johnny English");		
		Mission mission = missions.get(0);
		Long id = mission.getId();
		
		// set the title of the mission
		mission.setTitle("Rescue the world");	

		mission = da.getMission(id);
		
		// pass if the title equals to what the user tested
		assertEquals(mission.getTitle(), "Rescue the world");
	}
	
	@Test
	public void testDatabaseGetMission() {
		// retrieves the mission and field
		List<Mission> missions = da.getMissions("Johnny English");		
		Mission mission = da.getMission(missions.get(0).getId());
		
		// compare the title retrieved 
		assertEquals(mission.getTitle(), "Rescue the Queen");
	}

	@Test
	public void testDatabaseGetMissions() {
		// retrieves the mission and field
		List<Mission> missions = da.getMissions("Johnny English");
		Mission mission = missions.get(0);
		
		assertEquals(mission.getTitle(), "Rescue the Queen");
	}
}
