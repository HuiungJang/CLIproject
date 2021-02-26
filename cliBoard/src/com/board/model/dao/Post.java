package com.board.model.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Post {
    private ArrayList<String> content = new ArrayList<String>();
    // 파일 이름 저장위함 -> 게시판 글제목으로 출력

    private File f = new File(".");
    private String path = f.getAbsolutePath();
    // 절대경로 가져오기용



    public void makeDir(){
        // 게시글 저장할 디렉토리 만드는 메소드
        // board 디렉토리 없으면 생성.
        File makeDir = new File(path+File.separator+"board");

        if(!makeDir.exists()){
            makeDir.mkdir();
        }else
            return;

    }

    public void makeMemberInfoDir(){
        // 회원가입한 회원정보 저장할 디렉토리 만드는 메소드
        // memberInfo 디렉토리 없으면 생성.
        File makeDir = new File(path+File.separator+"memberInfo");

        if(!makeDir.exists()){
            makeDir.mkdir();
        }else
            return;

    }

    public void createPost(String title){
        // 게시글 생성
        // 입력값 전달받아서 파일 생성.

        makeDir();
        // board 디렉토리 없으면 생성.

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

    public void saveAdminInfo(){
        // 관리자 아이디를 생성하고 파일에 저장.

        Properties adminInfo = new Properties();
        adminInfo.setProperty("adminId","admin");
        // 관리자 아이디 "admin"
        adminInfo.setProperty("adminPsw","admin");
        // 관리자 비밀번호 "admin"

        try{
            adminInfo.store(new FileOutputStream("adminInfo.txt"),"adminData");

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public boolean checkInfo(String id, String psw){
        // 게시글 삭제위해 관리자 아이디와 비교하는 메소드.
        // id psw 비교하고 맞으면 true, 틀리면 false.

        Properties loadAdminInfo = new Properties();

        try{
            loadAdminInfo.load(new FileInputStream("adminInfo.txt"));

            if(loadAdminInfo.getProperty("adminId").equals(id) &&
                 loadAdminInfo.getProperty("adminPsw").equals(psw)) {
                return true;
            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletePost(String postTitle){
        File checkPost = new File(path+File.separator+"board"
                +File.separator+postTitle+".txt");

        if(checkPost.exists()){
            checkPost.delete();
            content.remove(postTitle);

            return true;

        }else
            return false;

    }


    public void postListPrint(){
        // 게시판 글 리스트 출력위한 메소드
//        int postNo = 1; // 글번호
//        for (int i = 0; i < content.size(); i++) {
//            String postList = content.get(i);
//            System.out.println( postNo +"\t\t\t" + postList );
//            postNo++;
//        }

        /*
         이렇게하면 실행시 번호와 리스트는 잘 나오고 유지되지만
         프로그램을 재시작하면 번호와 리스트가 초기화됨
         생각해본 해결책
         1. 파일이 저장된 디렉토리에 있는 파일 리스트를 읽어서 출력
         -> 그럴경우 글 번호를 어떻게 붙여야하지?
         -> 생성일자를 가져오고 파일제목 가져와서 붙여서 반환해야하나?

         해결 순서 ->
         1. 디렉토리에 있는 파일리스트를 읽어오기
         2. String[] 배열에 저장
         3. 배열값가져와서 반복문 돌려서 형식에 맞게 저장.
         4. 확장자 제거하고 출력
         */

        File checkPostList = new File(path+File.separator+"board");
        String[] list = checkPostList.list();
        // 디렉토리에있는 파일리스트 가져와서 배열에 저장.

        for(int i = 0; i< list.length; i++){
            String printList = (i+1)+"\t\t\t"+list[i];
            // 파일리스트를 글번호 글제목 형식에 맞게 printList에 저장.

            System.out.println(printList.replace(".txt",""));
            // 파일 확장자 제거.
        }

    }

    public boolean signUp(String id, String psw){
        makeMemberInfoDir();
        // 디렉토리 생성

        File memberInfoFile = new File(path+File.separator+"memberInfo"
                                         +File.separator+id+psw);
        // 아이디와 비밀번호를 이름으로 가진 파일 생성 -> 회원가입 끝

        try {
            File checkIdList = new File(path + File.separator + "memberInfo");
            String[] list = checkIdList.list();
            // 디렉토리에서 파일이름을 가져와서 저장.

            for (int i = 0; i<list.length; i++){
                if ( list[i].contains(id) ) {
                    // 파일이름에 아이디가 포함되어 있으면 -> 중복
                    return false; // 회원가입 실패
                }
            }
            // 위에 반복문이 다돌았다 -> 중복이 없다
            memberInfoFile.createNewFile();
            // 파일 생성
            return true;

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }


    public boolean signIn(String id, String psw){
        File checkIdList = new File(path + File.separator + "memberInfo");
        String[] list = checkIdList.list();

        for(int i =0; i<list.length; i++) {
            if (list[i].equals(id+psw)) {
                // 디렉토리에 id와 같은이름의 파일이 있으면
                return true;
                // 파일에서 value값 가져오고 그 값이 비밀번호와 같으면 트루
                // -> 로그인 성공

                // 문제점
                // 회원가입했을때 바로 로그인하면
                // 키,벨류값이 남아있어서 로그인이 됨.
                // 다시 로그인 하려면 키,벨류값이 초기화되어서 로그인이 안됨.

                // 생각해본 해결방안
                // 1. 파일이름을 아이디+비밀번호로 받고
                //    로그인시 그걸로 검증하기
            }else
                return false; // 로그인 실패
        }

        return false;
    }



}
