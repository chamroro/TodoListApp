package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 메뉴얼>");
        System.out.println("1 : 항목 추가");
        System.out.println("2 : 항목 삭제");
        System.out.println("3 : 항목 수정");
        System.out.println("4 : 전체 목록");
        System.out.println("5 : 카테고리 전체 목록");
        System.out.println("6 : 제목순 정렬");
        System.out.println("7 : 제목역순 정렬");
        System.out.println("8 : 날짜순 정렬");
        System.out.println("9 : 날짜역순 정렬");
        System.out.println("10 <keyword> : 키워드 검색");
        System.out.println("11 <category> : 카테고리 검색");
        System.out.println("12 : 중요도순 정렬");
        System.out.println("13 : 중요도역순 정렬");
        System.out.println("14 : 파일 저장");
        System.out.println("comp <comNum>");
    }
    
    public static void prompt() {
    	System.out.println("\n원하시는 명령어를 입력하세요 > ");
    }
}
