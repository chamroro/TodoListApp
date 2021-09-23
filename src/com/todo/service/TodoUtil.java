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
		
		String title, desc, current_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목추가]"+ "\n제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("이미 존재하는 제목은 추가할 수 없습니다.");
			return;
		}
		sc.nextLine();
		
		System.out.println("내용 > ");
		desc = sc.nextLine();
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy년 MM월 dd일 kk시 mm분 ss초");
		current_date= f.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, current_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		System.out.println("[항목삭제]\n"+ "제목 > ");
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목수정]n"+ "제목 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("입력한 제목이 존재하지 않습니다. ");
			return;
		}

		System.out.println("새로운 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다! ");
			return;
		}
		sc.nextLine();
		
		System.out.println("새로운 내용 > ");
		String new_description = sc.nextLine().trim();
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy/mm/dd kk:mm:ss");
		String current_date= f.format(new Date());
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, current_date);
				l.addItem(t);
				System.out.println("수정이 완료되었습니다. ");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("제목: " + item.getTitle() + " 설명: " + item.getDesc()+" - "+ item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("정보 저장이 완료되었습니다. ");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int i=0;
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st= new StringTokenizer(oneline,"##") ;
				i++;
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem s = new TodoItem(title,desc,current_date);
				l.addItem(s);
			}
			
			
			if(i == 0) {
				System.out.println("파일 로드 결과 : "+ filename + " 파일이 존재하지 않습니다. ");
			} else {
				System.out.println("파일 로드 결과 : "+ i+"개의 데이터를 읽었습니다.");
			}
			br.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}
}
