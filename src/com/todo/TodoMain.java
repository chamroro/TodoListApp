package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();

		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "1":
				TodoUtil.createItem(l);
				break;

			case "2":
				TodoUtil.deleteItem(l);
				TodoUtil.saveList(l, "todolist.txt");
				break;

			case "3":
				TodoUtil.updateItem(l);
				break;

			case "4":
				TodoUtil.listAll(l);
				break;
				
			case "5":
				TodoUtil.listCateAll(l);
				break;
				
			case "6":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "7":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;

			case "8":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "9":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "10":
				String keyword = sc.next();
				TodoUtil.findList(l, keyword);
				break;

			case "11":
				String cate = sc.next();
				TodoUtil.findCategory(l, cate);
				break;

			case "12":
				TodoUtil.saveList(l, "todolist.txt");
				System.out.println("파일이 저장되었습니다.");
				quit = true;
				break;

			case "help":
				Menu.displaymenu();
				break;
				
			default:
				System.out.println("정확한 입력어를 다시 입력하십시오. 도움말을 원하신다면 'help'를 입력하세요.");
				break;
			}

			if(isList) TodoUtil.listAll(l);
		} while (!quit);
	}
}