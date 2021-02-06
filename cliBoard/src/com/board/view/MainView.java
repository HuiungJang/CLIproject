package com.board.view;

import java.io.*;
import java.util.Scanner;

public class MainView {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    Scanner sc = new Scanner(System.in);
    private final String ID = "admin";
    private final String PSW = "admin";
    public void mainView() throws IOException {

        while(true) {
            bw.write("---- 게시글 목록 ----");
            bw.write("게시글을 읽으려면 게시글 번호를 입력하세요");
            bw.write("공사중");

            bw.write("게시글을 작성하려면 \"write\"를 입력하세요.");
            String writeB = br.readLine();

            bw.write("게시글을 삭제하려면 \"delete\"를 입력하세요.");
            String deleteB = br.readLine();

            if(writeB.equals("write")){
                //글 쓸 경우
                writePost();
            }else if(deleteB.equals("delete")){
                //글 삭제할 경우
                deletePost();
            }

            br.close();
            bw.flush();
            bw.close();
        }

    }

    public void writePost() throws  IOException{

        bw.write("---- 게시글 작성 ----");
        bw.write("게시글 제목을 입력해주세요");
        bw.write("내용을 입력해주세요.");


        br.close();
        bw.flush();
        bw.close();
    }

    public void deletePost() throws  IOException{

        if(checkInfo()){
            bw.write("---- 게시글 삭제 ----");
            bw.write("삭제할 게시글 번호 입력해주세요 : ");
            int postNum = sc.nextInt();


        }else {
            bw.write("권한이 없습니다. 목록으로 돌아갑니다.");
            return;
        }




        br.close();
        bw.flush();
        bw.close();
    }

    public boolean checkInfo() throws  IOException{
        boolean check = false;

        bw.write("아이디를 입력해주세요 : ");
        String id = br.readLine();
        bw.write("비밀번호를 입력해주세요 : ");
        String psw = br.readLine();

        if(id.equals(ID)&& psw.equals(PSW)){
            check = true;
            // 아이디 비밀번호 일치시
        }

        br.close();
        bw.flush();
        bw.close();

        return check;
    }

}
