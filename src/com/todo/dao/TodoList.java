package com.todo.dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.todo.service.*;

public class TodoList {
   Connection conn;

   public TodoList() {
      this.conn = DBConnection.getConnection();
   }

   public void importData(String filename) {
      try {
         BufferedReader br = new BufferedReader(new FileReader(filename));
         String line;
         String sql = "insert into list (title, memo, category, current_date, due_date, is_completed, importance, place)"
               + " values (?,?,?,?,?,?,?,?);";
         int records =0;
         while((line = br.readLine())!=null) {

            StringTokenizer st = new StringTokenizer(line, "##");
            String category = st.nextToken();
            String title = st.nextToken();
            String desc = st.nextToken();
            String due_date = st.nextToken();
            String current_date = st.nextToken();
            int is_completed = Integer.parseInt(st.nextToken());	
            int importance = Integer.parseInt(st.nextToken());	
            String place = st.nextToken();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setString(3, category);
            pstmt.setString(4, current_date);
            pstmt.setString(5, due_date);
            pstmt.setInt(6, is_completed);
            pstmt.setInt(7, importance);
            pstmt.setString(8, place);
            int count = pstmt.executeUpdate();
            if(count > 0) records++;
            pstmt.close();
         }
         System.out.println(records +" records read!");
         br.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public int addItem(TodoItem t) {
      String sql = "insert into list (title, memo, category, current_date, due_date, is_completed, importance, place)"
            + " values (?,?,?,?,?,?,?,?);";
      PreparedStatement pstmt;
      int count=0;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, t.getTitle());
         pstmt.setString(2, t.getDesc());
         pstmt.setString(3, t.getCategory());
         pstmt.setString(4, t.getCurrent_date());
         pstmt.setString(5, t.getDue_date());
         pstmt.setInt(6, t.getIs_completed());
         pstmt.setInt(7, t.getImportance());
         pstmt.setString(8, t.getPlace());
         
         count = pstmt.executeUpdate();
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      return count;
   }

   public ArrayList<TodoItem> getList() {
      ArrayList<TodoItem> list = new ArrayList<TodoItem>();
      Statement stmt;
      try {
         stmt = conn.createStatement();
         String sql = "SELECT * FROM list";
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String title = rs.getString("title");
            String description = rs.getString("memo");
            String due_date = rs.getString("due_date");
            String current_date = rs.getString("current_date");
            int is_completed = rs.getInt("is_completed");
            int importance = rs.getInt("importance");
            String place = rs.getString("place");
            TodoItem t = new TodoItem(title, description, category, due_date, is_completed, importance, place);
            t.setId(id);
            t.setCurrent_date(current_date);
            list.add(t);
         }
         stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return list;
   }

   public int getCount() {
      Statement stmt;
      int count = 0;
      try {
         stmt = conn.createStatement();
         String sql = "SELECT count(id) FROM list;";
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         count = rs.getInt("count(id)");
         stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return count;
   }

   public int updateItem(TodoItem t) {
      String sql ="update list set title=?, memo=?, category=?, current_date=?, due_date=?, is_completed=?, importance=?, place=?"
            + " where id = ?;";
      PreparedStatement pstmt;
      int count=0;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, t.getTitle());
         pstmt.setString(2, t.getDesc());
         pstmt.setString(3, t.getCategory());
         pstmt.setString(4, t.getCurrent_date());
         pstmt.setString(5, t.getDue_date());
         pstmt.setInt(6, t.getIs_completed());
         pstmt.setInt(7, t.getImportance());
         pstmt.setString(8, t.getPlace());
         pstmt.setInt(9, t.getId());
         
         count = pstmt.executeUpdate();
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return count;
   }

   public int deleteItem(int index) {
      String sql = "delete from list where id=?;";
      PreparedStatement pstmt;
      int count=0;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, index);
         count = pstmt.executeUpdate();
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return count;
   }

   public ArrayList<TodoItem> findList(String keyword) {
      ArrayList<TodoItem> list = new ArrayList<TodoItem>();
      PreparedStatement pstmt;
      keyword = "%"+keyword+"%";
      try {
         String sql = "SELECT * FROM list WHERE title like ? or memo like ?;";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1,keyword);
         pstmt.setString(2,keyword);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String title = rs.getString("title");
            String description = rs.getString("memo");
            String due_date = rs.getString("due_date");
            String current_date = rs.getString("current_date");
            int is_completed = rs.getInt("is_completed");
            int importance = rs.getInt("importance");
            String place = rs.getString("place");
            TodoItem t = new TodoItem(title, description, category, due_date, is_completed, importance, place);
            t.setId(id);
            t.setCurrent_date(current_date);
            list.add(t);
         }
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   return list;
   }      

   public ArrayList<String> getCategories() {
      ArrayList<String> list = new ArrayList<String>();
      Statement stmt;
      try {
         stmt = conn.createStatement();
         String sql = "SELECT DISTINCT category FROM list;";
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
             String category = rs.getString("category");
             list.add(category);
         }
      }catch (SQLException e) {
         e.printStackTrace();
      }
	return list;
   }

   public ArrayList<TodoItem> getListCategory(String keyword) {
	   ArrayList<TodoItem> list = new ArrayList<TodoItem>();
	   PreparedStatement pstmt;
	   try {
		   String sql = "SELECT * FROM list WHERE CATEGORY = ?";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, keyword);
		   ResultSet rs = pstmt.executeQuery();
		   while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("memo");
	            String due_date = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_completed = rs.getInt("is_completed");
	            int importance = rs.getInt("importance");
	            String place = rs.getString("place");
	            
	            TodoItem t = new TodoItem(title, description, category, due_date, is_completed, importance, place);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            list.add(t);
	         }
	         pstmt.close();
	   } catch(SQLException e) {
		   e.printStackTrace();
	   }
	   return list;
   }

   public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
	   ArrayList<TodoItem> list = new ArrayList<TodoItem>();
	   Statement stmt;
	   try {
		   stmt = conn.createStatement();
		   String sql = "SELECT * FROM list ORDER BY "+ orderby;
		   if (ordering == 0)
			   sql += " desc";
		   ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("memo");
	            String due_date = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_completed = rs.getInt("is_completed");
	            int importance = rs.getInt("importance");
	            String place = rs.getString("place");
	            TodoItem t = new TodoItem(title, description, category, due_date, is_completed, importance, place);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            list.add(t);
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   return list;
   }

	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public int completeItem(int comNum) {
		String sql = "update list set is_completed=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, comNum);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
}