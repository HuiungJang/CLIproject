package com.board.view;

import com.board.controller.Controller;

import java.io.*;
import java.util.Scanner;

public class MainView {
    Scanner sc = new Scanner(System.in);
    Controller c = new Controller();

    private final String ID = "admin";
    private final String PSW = "admin";


    public void mainView() throws IOException {

        while(true) {
            System.out.println("---- 게시글 목록 ----");
            System.out.println("게시글을 읽으려면 게시글 번호를 입력하세요");
            System.out.println("공사중");

            System.out.println("게시글을 작성하려면 \"write\"를 입력하세요.");
            System.out.println("게시글을 삭제하려면 \"delete\"를 입력하세요.");
            System.out.println("원하는 항목을 입력해주세요 : ");
            String post = sc.nextLine();

            if (post.equals("write")) {
                //글 쓸 경우
                writePost();
            } else if (post.equals("delete")) {
                //글 삭제할 경우
                deletePost();
            }


        }

    }

    public void writePost(){

        System.out.println("---- 게시글 작성 ----");
        System.out.println("게시글 제목을 입력해주세요 : ");
        String postName = sc.nextLine();
        c.createPostName(postName);
        // 입력값 받아서 txt파일 생성(게시글 제목으로 쓸 예정)

        System.out.println("내용을 입력해주세요. : ");
        boolean stopInput = true;
        do{
            String content = sc.nextLine();
            c.createPostContent(content);
            // 내용입력
            System.out.println("더 입력하시겠습니까?(Y/N)");
            String cho = sc.nextLine();

            if( cho.toUpperCase().contains("N") ){
                // 입력값 대문자로 변환후 N있으면
                // 반복문 종료
                stopInput = false;
            }

        }while(stopInput);
    }

    public void deletePost() {

        if(checkInfo()){
            System.out.println("---- 게시글 삭제 ----");
            System.out.println("삭제할 게시글 번호 입력해주세요 : ");
            int postNum = sc.nextInt();
            sc.nextLine();

        }else {
            System.out.println("권한이 없습니다. 목록으로 돌아갑니다.");
            return;
        }




    }

    public boolean checkInfo() {
        boolean check = false;

        System.out.println("관리자 아이디를 입력해주세요 : ");
        String id = sc.nextLine();

        System.out.println("관리자 비밀번호를 입력해주세요 : ");
        String psw = sc.nextLine();

        if(id.equals(ID)&& psw.equals(PSW)){
            check = true;
            // 아이디 비밀번호 일치시
        }


        return check;
    }

}
