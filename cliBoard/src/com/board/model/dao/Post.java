package com.board.model.dao;

import java.io.*;
import java.util.ArrayList;

public class Post {
    private ArrayList<String> content = new ArrayList<String>();
    // 파일 이름 저장위함 -> 게시판 글제목으로 출력

    private final String id = "admin";
    private final String psw = "admin";
    // 글 삭제위한 id,psw

    private File f = new File(".");
    private String path = f.getAbsolutePath();
    // 절대경로 가져오기용

    public void createPost(String title){
        // 게시글 생성
        // 입력값 전달받아서 파일 생성.

        File createPostFile = new File(path+File.separator+"board"
                +File.separator+title+".txt");

        try{
            createPostFile.createNewFile();

        }catch(IOException e){
            e.printStackTrace();
        }

        String fileName = createPostFile.getName();
        content.add(fileName.replace(".txt",""));
        // 확장자 제거하고 파일이름만 저장.

    }


    public void createPostContent(String title, String content){
        // 게시글 내용 넣기
        // 입력받은 제목의 파일을 찾고
        // 그 파일에 내용 넣기

        try( FileWriter fw = new FileWriter(path + File.separator + "board"
                + File.separator + title+".txt",true)) {

            fw.write(content+"\n");

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void viewPost(String postTitle){
        // 게시물 조회
        // 입력받은 제목의 파일을 읽기

        try (FileReader fr = new FileReader(path + File.separator + "board"
                + File.separator + postTitle + ".txt")) {

            BufferedReader br = new BufferedReader(fr);

            String text = "";

            while( (text = br.readLine()) != null){
                System.out.println(text);
            }

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


    }



    public boolean checkInfo(String id, String psw){
        // 게시글 삭제위해 관리자 아이디와 비교하는 메소드.
        // id psw 비교하고 맞으면 true, 틀리면 false.

        if( this.id.equals(id) && this.psw.equals(psw) ){
            return true;
        }else
            return false;

    }

    public boolean deletePost(String postTitle){
        File checkPost = new File(path+File.separator+"board"
                +File.separator+postTitle+".txt");

        if(checkPost.exists()){

            checkPost.delete();
            return true;

        }else
            return false;

    }


    public void postListPrint(){
        // 게시판 글 리스트 출력위한 메소드
        int postNo = 1; // 글번호
        for (int i = 0; i < content.size(); i++) {
            String postList = content.get(i);
            System.out.println( postNo +"\t\t\t" + postList );
            postNo++;
        }

    }



}
