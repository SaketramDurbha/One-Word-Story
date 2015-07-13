package me.onewordstory.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import me.onewordstory.util.Database;
import me.onewordstory.websockets.WebSocketImpl;

@Path("/words")
public class WordService {
	
	@Context 
	private HttpServletRequest req;
	
	@Context
	private HttpServletResponse resp;
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Integer addWord(@FormParam(value = "word") String word) throws IOException
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();
		int rows = 0;
		
		String sql = "INSERT INTO words (word) VALUES (?);";
		
		PreparedStatement ps = null;
		WebSocketImpl.broadcast("newWord", word);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, word);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.freeConnection(conn);
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
	

	
}
