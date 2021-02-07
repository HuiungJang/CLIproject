package com.board.controller;

import java.io.*;

public class Controller {

    public void  createPostName(String postName){
        String path = Controller.class.getResource("/").getPath();
        // 이 클래스의 경로를 가져오고
       path = path.substring(0,path.lastIndexOf("out"));
        //  out디렉토리 밖으로 빠져나오기

        File f = new File(path+"/cliBoard/boardPost/"+postName+".txt");
          // 게시물 저장폴더인 boardPost폴더에 입력값을 받아서 txt파일로 저장.
        try {
            if(f.createNewFile()) {
                System.out.println("게시글이 등록되었습니다.");
            }else {
                System.out.println("게시글 등록실패 다시 시도해주세요.");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void createPostContent(String content,String postName){

        try ( Writer w = new FileWriter("cliBoard/boardPost/" + postName + ".txt",true) ) {
            // true 를 적을경우 경로에 파일이 있으면 내용을 이어적는다.
            // PrintWriter 은 입력한 문자열을 개행 할 수 있따.
            // println()메소드 사용가능하다.
            // 대신 반복해서 입력받을시 이전 내용은 지워지고 새로운 내용만 남는다.
            w.write(content+"\n");
            // 내용을 적고 개행.

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void getPostList(){

        String path = Controller.class.getResource("/").getPath();
        // 이 클래스의 경로를 가져오고
        path = path.substring(0,path.lastIndexOf("out"));
        //  out디렉토리 밖으로 빠져나오기

        File f = new File(path+"/cliBoard/boardPost/");
        String[] postList = f.list();
        // 경로에 있는 파일 목록을 가져와서
        // postList배열에 저장.
        int count =0;
        for (String tmp : postList) {

            String tmp1 = tmp.substring(0,tmp.lastIndexOf("."));
            // 확장자 제거
            System.out.println("\t\t"+ tmp1);
        }

    }

}
