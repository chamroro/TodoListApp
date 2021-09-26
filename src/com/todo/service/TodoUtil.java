package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		int index;
		String title, desc, current_date, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목추가]"+ "\n카테고리 > ");
		
		category = sc.nextLine();
		
		System.out.println("제목 > ");
		title = sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.printf("이미 존재하는 제목은 추가할 수 없습니다.");
			return;
		}
		
		
		System.out.println("내용 > ");
		desc = sc.nextLine();
		
		System.out.println("마감일자 > ");
		due_date= sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		System.out.println("[항목삭제]\n"+ "삭제할 항목의 번호를 입력하세요. > ");
		Scanner sc = new Scanner(System.in);
		int index= sc.nextInt();
		l.deleteItem(l.getList().get(index-1));
		System.out.println("삭제가 완료되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목수정]"+ "\n수정할 항목의 번호를 입력하세요. > ");
		int index = sc.nextInt();
		l.deleteItem(l.getList().get(index-1));
		
		System.out.println("새로운 카테고리 > ");
		String new_category = sc.next().trim();
		
		System.out.println("새로운 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다! ");
			return;
		}
		sc.nextLine();
		
		System.out.println("새로운 내용 > ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("새로운 마감기한 > ");
		String new_duedate = sc.nextLine().trim();
		
		
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_duedate);
		l.addItem(t);
		System.out.println("수정이 완료되었습니다. ");
			
	}

	public static void listAll(TodoList l) {
		int index=0;
		System.out.println("[전체 목록, 총 "+ l.getList().size()+"개]");
		for (TodoItem item : l.getList()) {
			index++;
			System.out.println(index+". ["+ item.getCategory() + "] 제목: " + item.getTitle() + " 설명: " + item.getDesc()+" - "+ item.getCurrent_date()+" - "+item.getDue_date() +" "+ item.getCurrent_time());
			
		}
	}
	
	public static void listCate(TodoList l) {
		Set<String> categorySet = new HashSet<String>();
		
		for(TodoItem item: l.getList()) {
			categorySet.add(item.getCategory());
		}
		
		int count=0;
		for(String item: categorySet) {
			System.out.println("- "+ item);
			count++;
		}
		System.out.println("\n총 "+count+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void findList(TodoList l, String keyword) {
		keyword = keyword.trim();
		int index=0;
		int count=0;
		for (TodoItem item : l.getList()) {
			index++;
			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
				System.out.println(index+". ["+ item.getCategory() + "] 제목: " + item.getTitle() + " 설명: " + item.getDesc()+" - "+ item.getCurrent_date()+" - "+item.getDue_date() +" "+ item.getCurrent_time());
				count++;
			} 
		} 
		System.out.println("총 "+count+"개의 항목을 찾았습니다. ");
		
	}
	
	public static void findCateList(TodoList l, String keyword) {
		keyword = keyword.trim();
		int index=0;
		int count=0;
		for (TodoItem item : l.getList()) {
			index++;
			if(item.getCategory().contains(keyword)) {
				System.out.println(index+". ["+ item.getCategory() + "] 제목: " + item.getTitle() + " 설명: " + item.getDesc()+" - "+ item.getCurrent_date()+" - "+item.getDue_date() +" "+ item.getCurrent_time());
				count++;
			} 
		} 
		System.out.println("총 "+count+"개의 항목을 찾았습니다. ");
		
	}
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem i: l.getList()) 
				w.write(i.toSaveString());
			System.out.println("데이터가 저장되었습니다.");
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int count=0;
			
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				String current_time = st.nextToken();
				TodoItem item= new TodoItem(category, title, desc, due_date);
				item.setCurrent_date(current_date);
				item.setCurrent_time(current_time);
				l.addItem(item);
				count++;
			}
			br.close();
			if(count != 0) System.out.println(count+"개의 항목을 읽었습니다.");
			else System.out.println(filename + "파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
