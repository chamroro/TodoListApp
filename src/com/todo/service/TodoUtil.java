package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("todolist.txt"));
			String oneline;
			while((oneline = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				int is_completed = Integer.parseInt(st.nextToken());
				int importance = Integer.parseInt(st.nextToken());
				String place = st.nextToken();

				TodoItem t = new TodoItem(title, desc, category, due_date, is_completed, importance, place);
				t.setCurrent_date(current_date);
				l.addItem(t);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void createItem(TodoList l) {
		int is_completed, importance;
		String title, desc, category, due_date, place ;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 추가]\n"
				+ "제목 > ");

		title = sc.nextLine();

		if (l.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}

		System.out.print("카테고리 > ");
		category = sc.nextLine();

		System.out.print("내용 > ");
		desc = sc.nextLine();

		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		System.out.print("중요도(1-5) > ");
		importance = sc.nextInt();
		
		System.out.print("장소 > ");
		place = sc.next();
			
		
		is_completed=0;

		TodoItem t = new TodoItem(title, desc, category, due_date, is_completed, importance, place );
		if(l.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);

		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		String index = sc.nextLine();
		
		String isdeleted="";
		StringTokenizer st = new StringTokenizer(index);
		while(st.hasMoreTokens()) {
			int n = Integer.parseInt(st.nextToken());
			if(l.deleteItem(n)>0) {
				isdeleted += n+"/";
				
			}
		}
		System.out.printf("%s를 삭제하였습니다.", isdeleted);
	}


	public static void updateItem(TodoList l) {
		int new_is_completed, new_importance;
		String new_title, new_desc, new_category, new_due_date, new_place;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");

		int index = sc.nextInt();
		
		System.out.print("제목 > ");
		new_title = sc.next();

		System.out.print("카테고리 > ");
		new_category = sc.next();

		System.out.print("내용 > ");
		new_desc = sc.next();

		System.out.print("마감일자 > ");
		new_due_date = sc.next();
		
		System.out.print("중요도(1-5) > ");
		new_importance = sc.nextInt();
		
		System.out.print("장소 > ");
		new_place = sc.next();
			
		new_is_completed=0;

		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, new_is_completed, new_importance, new_place);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");
		
	
	}

	public static void findList(TodoList l, String keyword) {		
		int count = 0;

		for(TodoItem item: l.findList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.", count);
	}

	public static void findCategory(TodoList l, String category) {
		int count = 0, i = 0;
		for(TodoItem item : l.getList() ) {
			i++;
			if(item.getCategory().equals(category)) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void listCategory(TodoList l) {
		Set<String> list = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			list.add(item.getCategory());
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			String st = (String)it.next();
			System.out.print(st);
			if (it.hasNext()) {
				System.out.print(" / ");
			}
		}
		System.out.println("\nTotal of " + list.size() + " different categories in the list.");
	}

	public static void listAll(TodoList l) {
		int i = 0;
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());

		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, String comNum) {
		String ischecked="";
		StringTokenizer st = new StringTokenizer(comNum);
		while(st.hasMoreTokens()) {
			int n = Integer.parseInt(st.nextToken());
			if(l.completeItem(n)==1) {
				ischecked += n+"/";
				
			}
		}
		System.out.printf("%s를 완료 체크하였습니다.", ischecked);
	}
}