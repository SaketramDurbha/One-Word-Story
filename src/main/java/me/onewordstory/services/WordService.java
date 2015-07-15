package me.onewordstory.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import me.onewordstory.entites.*;
import me.onewordstory.util.Database;
import me.onewordstory.websockets.WebSocketImpl;

@Path("/words")
public class WordService {

	public static final String begPara = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp";
	public static final String begBook = 
			"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;Swinging"+"my"+"hammock"+"gracefully"+"back"+"and"+"forth,"+"the"+"night"+"sky"+"stayed"+"above"+"me"+"for"+"the"+"long"+"length"+"as"+"I"+"looked"+"at"+"it"+"in"+"silence."+"But"+"for"+"the"+"universe,"+"two"+"hours"+"is"+"absolutely"+"nothing."+"I"+"was"+"thinking"+"purely"+"about";

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
		WebSocketImpl.broadcast("newWord",word);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,word);
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
		String sql = "UPDATE words SET sentence_id = ? WHERE sentence_id = NULL";
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
			ps.setInt(1,sentId);
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
		String sql = "UPDATE sentences SET paragraph_id = ? WHERE paragraph_id = NULL";
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
			ps.setInt(1,sentId);
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
		String sql = "UPDATE paragraphs SET chapter_id = ? WHERE chapter_id = NULL";
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
			ps.setInt(1,sentId);
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
		String sql = "UPDATE chapters SET book_id = ? WHERE book_id = NULL";
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
			ps.setInt(1,sentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			data.freeConnection(conn);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Book[] getBooks ()
	{
		ArrayList<Book> books = new ArrayList<Book>();
		HashMap<Integer,ArrayList<Chapter>> chapters = new HashMap<Integer,ArrayList<Chapter>>();
		HashMap<Integer,ArrayList<Paragraph>> paragraphs = new HashMap<Integer,ArrayList<Paragraph>>();
		HashMap<Integer,ArrayList<Sentence>> sentences = new HashMap<Integer,ArrayList<Sentence>>();
		HashMap<Integer,ArrayList<Word>> words = new HashMap<Integer,ArrayList<Word>>();

		Database data = Database.getInstance();
		Connection conn = data.getConnection();

		String bookSQL = "SELECT * FROM books;";
		String chapSQL = "SELECT * FROM chapters;";
		String paraSQL = "SELECT * FROM paragraphs;";
		String sentSQL = "SELECT * FROM sentences;";
		String wordSQL = "SELECT * FROM words;";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(bookSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Book book = new Book();
				book.book_id = rs.getInt("book_id");
				books.add(book);
			}
			rs.close();
			ps.close();

			ps = conn.prepareStatement(chapSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Chapter chap = new Chapter();
				chap.book_id = rs.getInt("book_id");
				if (rs.wasNull())
				{
					chap.book_id = 0;
				}
				chap.chapter_id = rs.getInt("chapter_id");

				if (chapters.get(chap.book_id) == null) chapters.put(chap.book_id, new ArrayList<Chapter>());
				chapters.get(chap.book_id).add(chap);

			}
			rs.close();
			ps.close();

			ps = conn.prepareStatement(paraSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Paragraph paras = new Paragraph();
				paras.chapter_id = rs.getInt("chapter_id");
				if (rs.wasNull())
				{
					paras.chapter_id = 0;
				}
				paras.paragraph_id = rs.getInt("paragraph_id");
				if (paragraphs.get(paras.chapter_id) == null) paragraphs.put(paras.chapter_id, new ArrayList<Paragraph>());
				paragraphs.get(paras.chapter_id).add(paras);

			}
			rs.close();
			ps.close();

			ps = conn.prepareStatement(sentSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Sentence sent = new Sentence();
				sent.paragraph_id = rs.getInt("paragraph_id");
				if (rs.wasNull())
				{
					sent.paragraph_id = 0;
				}
				sent.sentence_id = rs.getInt("sentence_id");
				if (sentences.get(sent.paragraph_id) == null) sentences.put(sent.paragraph_id, new ArrayList<Sentence>());
				sentences.get(sent.paragraph_id).add(sent);
			}
			rs.close();
			ps.close();

			ps = conn.prepareStatement(wordSQL);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Word word = new Word();
				word.sentence_id = rs.getInt("sentence_id");
				System.out.println(rs.getString("word"));
				word.word = rs.getString("word");
				if (rs.wasNull())
				{
					word.sentence_id = 0;
				}
				word.word_id = rs.getInt("word_id");
				if (words.get(word.word_id) == null) words.put(word.sentence_id, new ArrayList<Word>());
				words.get(word.sentence_id).add(word);
			}

		} catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.freeConnection(conn);
		}

		for (int i = 0; i < books.size(); i++)
		{
			ArrayList<Chapter> chaps = chapters.get(books.get(i).book_id);
			Chapter[] chappies = new Chapter[chaps.size()];
			chaps.toArray(chappies);
			Book book = books.get(i);
			book.chapters = chappies;
			books.set(i, book);
			for (int j = 0; j <chappies.length; j++)
			{
				ArrayList<Paragraph> paras = paragraphs.get(chappies[j].chapter_id);
				Paragraph[] paraas = new Paragraph[paras.size()];
				paras.toArray(paraas);
				Chapter chap = chappies[j];
				chap.paras = paraas;
				chappies[j] = chap;

				for (int k = 0; k < paraas.length; k++)
				{
					ArrayList<Sentence> sents = sentences.get(paraas[k].paragraph_id);
					Sentence[] senties = new Sentence[sents.size()];
					sents.toArray(senties);
					Paragraph para = paraas[k];
					para.sents = senties;
					paraas[k] = para;

					for (int l = 0; l < senties.length; l++)
					{

						ArrayList<Word> lesWords = words.get(senties[k].sentence_id);
						Word[] wordies = new Word[lesWords.size()];
						lesWords.toArray(wordies);
						Sentence sent = senties[k];
						sent.words = wordies;
						senties[k] = sent;
					}
				}
			}
		}

		Book[] boooks = new Book[books.size()];
		books.toArray(boooks);

		return boooks;
	}

	@Path("/word/null")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Word[] getNullWords()
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM words WHERE sentence_id IS NULL;";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Word> comps = new ArrayList<Word>(); 
			while (rs.next())
			{
				Word word = new Word();
				word.sentence_id = 0;
				word.word = rs.getString("word");
				word.word_id = rs.getInt("word_id");
				comps.add(word);
			}
			Word[] words = new Word[comps.size()];
			comps.toArray(words);
			return words;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.freeConnection(conn);
		}
		return null;
	}
	
	@Path("/sentence/null")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Sentence[] getNullSents()
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PreparedStatement inPs = null;
		ResultSet inRs = null;

		String sql = "SELECT * FROM sentences WHERE paragraph_id IS NULL;";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Sentence> comps = new ArrayList<Sentence>();
			ArrayList<Word> lesWords = new ArrayList<Word>();
			while (rs.next())
			{
				Sentence sent = new Sentence();
				sent.paragraph_id = 0;
				sent.sentence_id = rs.getInt("sentence_id");
				
				
				String inSQL = "SELECT * FROM words WHERE sentence_id = ?;";
				inPs = conn.prepareStatement(inSQL);
				inPs.setInt(1, sent.sentence_id);
				inRs = inPs.executeQuery();
				
				while (inRs.next())
				{
					Word word = new Word();
					word.sentence_id = sent.sentence_id;
					word.word = inRs.getString("word");
					word.word_id = inRs.getInt("word_id");
					
					lesWords.add(word);
					
				}
				
				Word[] words = new Word[lesWords.size()];
				lesWords.toArray(words);
				sent.words = words;
				comps.add(sent);
				
				lesWords.clear();
				
			}
			Sentence[] sents = new Sentence[comps.size()];
			comps.toArray(sents);
			return sents;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				inRs.close();
				inPs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.freeConnection(conn);
		}
		return null;
	}
	
	@Path("/paragraph/null")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Paragraph[] getNullParas()
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PreparedStatement inPs = null;
		ResultSet inRs = null;
		
		PreparedStatement inInPs = null;
		ResultSet inInRs = null;

		
		try {
			String sql = "SELECT * FROM paragraphs WHERE chapter_id IS NULL;";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Paragraph> comps = new ArrayList<Paragraph>();
			ArrayList<Sentence> lesSents = new ArrayList<Sentence>();
			ArrayList<Word> lesWords = new ArrayList<Word>();
			while (rs.next())
			{
				Paragraph para = new Paragraph();
				para.chapter_id = 0;
				para.paragraph_id = rs.getInt("paragraph_id");
				
				
				String inSQL = "SELECT * FROM sentences WHERE paragraph_id = ?;";
				inPs = conn.prepareStatement(inSQL);
				inPs.setInt(1, para.paragraph_id);
				inRs = inPs.executeQuery();
				String inInSQL = "SELECT * FROM words WHERE sentence_id = ?";
				while (inRs.next())
				{
					Sentence sent = new Sentence();
					sent.paragraph_id = para.paragraph_id;
					sent.sentence_id = inRs.getInt("sentence_id");
					inInPs = conn.prepareStatement(inInSQL);
					inInPs.setInt(1, sent.sentence_id);
					inInRs = inInPs.executeQuery();
					while (inInRs.next())
					{
						Word word = new Word();
						word.sentence_id = sent.sentence_id;
						word.word = inInRs.getString("word");
						word.word_id = inInRs.getInt("word_id");
						
						lesWords.add(word);
					}
					Word[] words = new Word[lesWords.size()];
					lesWords.toArray(words);
					sent.words = words;
					lesSents.add(sent);
					
					lesWords.clear();
				}
				
				Sentence[] sents = new Sentence[lesSents.size()];
				lesSents.toArray(sents);
				para.sents = sents;
				comps.add(para);
				
				lesSents.clear();
				
			}
			Paragraph[] paras = new Paragraph[comps.size()];
			comps.toArray(paras);
			return paras;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				ps.close();
				rs.close();
				inPs.close();
				inRs.close();
				inInPs.close();
				inInRs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.freeConnection(conn);
		}
		return null;
	}
	
	@Path("/chapter/null")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Chapter[] getNullChaps()
	{
		Database data = Database.getInstance();
		Connection conn = data.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PreparedStatement inPs = null;
		ResultSet inRs = null;
		
		PreparedStatement inInPs = null;
		ResultSet inInRs = null;
		
		PreparedStatement inInInPs = null;
		ResultSet inInInRs = null;

		
		try {
			String sql = "SELECT * FROM chapters WHERE book_id IS NULL;";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Chapter> comps = new ArrayList<Chapter>();
			ArrayList<Paragraph> lesParas = new ArrayList<Paragraph>();
			ArrayList<Sentence> lesSents = new ArrayList<Sentence>();
			ArrayList<Word> lesWords = new ArrayList<Word>();
			while (rs.next())
			{
				Chapter chap = new Chapter();
				chap.book_id = 0;
				chap.chapter_id = rs.getInt("chapter_id");
				
				
				String inSQL = "SELECT * FROM paragraphs WHERE chapter_id = ?;";
				inPs = conn.prepareStatement(inSQL);
				inPs.setInt(1, chap.chapter_id);
				inRs = inPs.executeQuery();
				String inInSQL = "SELECT * FROM sentences WHERE paragraph_id = ?";
				while (inRs.next())
				{
					Paragraph para = new Paragraph();
					para.chapter_id = chap.chapter_id;
					para.paragraph_id = inRs.getInt("paragraph_id");
					inInPs = conn.prepareStatement(inInSQL);
					inInPs.setInt(1, para.paragraph_id);
					inInRs = inInPs.executeQuery();
					while (inInRs.next())
					{
						Sentence sent = new Sentence();
						sent.paragraph_id = para.paragraph_id;
						sent.sentence_id = inInRs.getInt("sentence_id");
						String inInInSql = "SELECT * FROM words WHERE sentence__id = ?";
						inInInPs = conn.prepareStatement(inInInSql);
						inInInPs.setInt(1, sent.sentence_id);
						inInInRs = inInInPs.executeQuery();
						while (inInInRs.next())
						{
							Word word = new Word();
							word.sentence_id = sent.sentence_id;
							word.word = inInInRs.getString("word");
							word.word_id = inInInRs.getInt("word_id");
							
							lesWords.add(word);
						}
						Word[] words = new Word[lesWords.size()];
						lesWords.toArray(words);
						sent.words = words;
						lesSents.add(sent);
						lesWords.clear();
					}
					Sentence[] sents = new Sentence[lesSents.size()];
					lesSents.toArray(sents);
					para.sents = sents;
					lesParas.add(para);
					
					lesSents.clear();
				}
				
				Paragraph[] paras = new Paragraph[lesParas.size()];
				lesParas.toArray(paras);
				chap.paras = paras;
				comps.add(chap);
				
				lesParas.clear();
				
			}
			Chapter[] chaps = new Chapter[comps.size()];
			comps.toArray(chaps);
			return chaps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				ps.close();
				rs.close();
				inPs.close();
				inRs.close();
				inInPs.close();
				inInRs.close();
				inInInPs.close();
				inInInRs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.freeConnection(conn);
		}
		return null;
	}

}