package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 메뉴얼>");
        System.out.println("1. add: 항목 추가");
        System.out.println("2. del : 항목 삭제");
        System.out.println("3. edit : 항목 수정");
        System.out.println("4. ls : 전체 목록");
        System.out.println("5. ls_cate : 카테고리 전체 목록");
        System.out.println("6. ls_name_asc : 제목순 정렬");
        System.out.println("7. ls_name_desc : 제목역순 정렬");
        System.out.println("8. ls_date : 날짜순 정렬");
        System.out.println("9. ls_date_desc : 날짜순 정렬");
        System.out.println("10. find <keyword> : 키워드 검색");
        System.out.println("11. find <category> : 카테고 검색");
        System.out.println("12. exit : 종료");
    }
    
    public static void prompt() {
    	System.out.println("\n원하시는 명령어를 입력하세요 > ");
    }
}
