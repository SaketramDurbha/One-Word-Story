package me.onewordstory.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public static final String begPara = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp";
	public static final String[] begBook = 
		{"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;Swinging", "my", "hammock", "gracefully", "back", "and", "forth,", "the", "night", "sky", "stayed", "above", "me", "for", "the", "long", "length", "as", "I", "looked", "at", "it", "in", "silence.", "But", "for", "the", "universe,", "two", "hours", "is", "absolutely", "nothing.", "I", "was", "thinking", "purely", "about"};

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

	@Path("/newsentence")
	@POST
	public void newSentence() 
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();
		String sql1 = "INSERT INTO sentences () VALUES ();";
		String sql2 = "SELECT sentence_id FROM sentences ORDER BY sentence_id DESC LIMIT 1;";
		String sql = "UPDATE words SET sentence_id = ? WHERE sentence_id = 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			rs.next();
			int sentId = rs.getInt("sentence_id");
			rs.close();
			ps.close();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			data.freeConnection(conn);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Path("/newparagraph")
	@POST
	public void newParagraph() 
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();
		String sql1 = "INSERT INTO paragraphs () VALUES ();";
		String sql2 = "SELECT paragraph_id FROM paragraphs ORDER BY paragraph_id DESC LIMIT 1;";
		String sql = "UPDATE sentences SET paragraph_id = ? WHERE paragraph_id = 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			rs.next();
			int sentId = rs.getInt("paragraph_id");
			rs.close();
			ps.close();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			data.freeConnection(conn);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	@Path("/newchapter")
	@POST
	public void newChapter() 
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();
		String sql1 = "INSERT INTO chapters () VALUES ();";
		String sql2 = "SELECT chapter_id FROM chapters ORDER BY chapter_id DESC LIMIT 1;";
		String sql = "UPDATE paragraphs SET chapter_id = ? WHERE chapter_id = 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			rs.next();
			int sentId = rs.getInt("chapter_id");
			rs.close();
			ps.close();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			data.freeConnection(conn);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	@Path("/newbook")
	@POST
	public void newBook() 
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();
		String sql1 = "INSERT INTO books () VALUES ();";
		String sql2 = "SELECT book_id FROM books ORDER BY book_id DESC LIMIT 1;";
		String sql = "UPDATE chapters SET book_id = ? WHERE book_id = 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			rs.next();
			int sentId = rs.getInt("book_id");
			rs.close();
			ps.close();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			data.freeConnection(conn);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}