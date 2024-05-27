package ca.sheridancollege.database;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Mission;

/**
 * This is the database class. It includes the data and methods to manipulate
 * the data.
 * 
 * @author Mihye 
 */
@Repository
public class DatabaseAccess {
	
	private NamedParameterJdbcTemplate jdbc;

	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public int addMission(Mission mission) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "INSERT INTO missions (agent, title, gadget1, gadget2) "
				+ "VALUES (:agent, :title, :gadget1, :gadget2)";

		namedParameters
			.addValue("agent", mission.getAgent())
			.addValue("title", mission.getTitle())
			.addValue("gadget1", mission.getGadget1())
			.addValue("gadget2", mission.getGadget2());

		int returnValue = jdbc.update(query, namedParameters);
		return returnValue;
	}

	public Mission getMission(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM missions WHERE id = :id";

		namedParameters.addValue("id", id);

		BeanPropertyRowMapper<Mission> missionMapper = 
				new BeanPropertyRowMapper<Mission>(Mission.class);

		// now update with the new data and return row
		List<Mission> missions = 
				jdbc.query(query, namedParameters, missionMapper);

		// if the list is empty, return null. Otherwise, return the first one
		if (missions.isEmpty()) {
			return null; // error condition
		} else {
			return missions.get(0);
		}
	}

	public int updateMission(Mission mission) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = 
				"UPDATE missions SET agent = :agent, title = :title, gadget1 = :gadget1"
				+ ", gadget2 = :gadget2 WHERE id = :id";

		namedParameters
			.addValue("agent", mission.getAgent())
			.addValue("title", mission.getTitle())
			.addValue("gadget1", mission.getGadget1())
			.addValue("gadget2", mission.getGadget2())
			.addValue("id", mission.getId());

		int returnValue = jdbc.update(query, namedParameters);
		return returnValue;
	}

	public int deleteMission(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "DELETE FROM missions WHERE id= :id";

		// add parameters to the map
		namedParameters.addValue("id", id);

		// now update with the new data and return number of rows affected
		int returnValue = jdbc.update(query, namedParameters);

		return returnValue;
	}

	public List<Mission> getMissions(String agent) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM missions WHERE agent = :agent";

		namedParameters.addValue("agent", agent);

		BeanPropertyRowMapper<Mission> missionMapper = 
				new BeanPropertyRowMapper<Mission>(Mission.class);

		List<Mission> missions = 
				jdbc.query(query, namedParameters, missionMapper);
		return missions;
	}
}
