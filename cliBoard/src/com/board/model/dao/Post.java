package com.board.model.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void makeDateDir(){
        File makeDateDir = new File(path+File.separator+"postdate");
        // 파일생성 일자를 가지는 파일을 저장할 디렉토리 만들기
        // 없으면 디렉토리 생성.

        if(!makeDateDir.exists()){
            makeDateDir.mkdir();
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

        makeDateDir();
        // postdate 디렉토리 없으면 생성.

        File createPostFile = new File(path+File.separator+"board"
                +File.separator+title+".txt");

        try{
            createPostFile.createNewFile();
            String fileName = createPostFile.getName();

            content.add(fileName.replace(".txt","") );
            // 확장자 제거하고 파일이름만 저장.

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /*
    파일 생성일자를 파일이름과 같이 출력하고 싶다.
       문제점
        1. 생성일자를 String 타입으로 저장함 -> 프로그램 재시작하면 유지가 안됨.
        해결책
        - 1. 파일 생성시 파일이름에 생성일자 붙여서 출력
            -> 파일 생성돼야 생성일자가 나오는데 그러면 이미 파일이름에 생성일자를 붙일수가 없음. => 불가

        - 2. 완성된 파일에 생성일자 붙여서 다시 저장
            -> 파일에 내용을 추가할때 파일이름으로 해당파일을 찾는데
                날짜까지 붙여서 찾아야하고 언제인지 일일히 봐가면서 해야함 => 불가

        - 3. 파일 생성시 board 디렉토리에 게시글 제목을 가진 파일 1개 만들고
             새로운 디렉토리에 그 파일의 생성일자를 제목으로 하는 파일을 만들어서 저장
             메인에서 게시글 출력시 board 디렉토리에서 1개 출력 생성일자 디렉토리에서 1개 출력하면 될
            => 해결
                아쉬운점 만약, 게시글 생성시 작성일자 파일이 없이 생성됐다면 작성일자는 없이 출력됨.

     */

    public void createPostDate(String title){
        // 게시글 작성일자를 가진 파일만들기.
        BasicFileAttributes attrs = null;

        File postPath = new File(path+File.separator+"board"
                +File.separator+title+".txt");
        // 게시글 경로 가져오기

        try {
            attrs = Files.readAttributes(postPath.toPath(), BasicFileAttributes.class);
            // 경로에 있는 파일의 속성값 가져온다.

            FileTime time = attrs.creationTime();
            // 파일의 속성값중 만들어진 시간을 가져와서 time에 저장.

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            // pattern의 형식대로 날짜를 출력하겠다.

            String formatted = simpleDateFormat.format(new Date(time.toMillis()));
            // 파일이 만들어진 시간을 pattern 형식대로 변환.
            // 여기까지가 게시글 파일 이름 가져와서
            // 그 파일에 대한 생성일자 가져와서 formatted 에 저장.

            File postDate = new File(path+File.separator+"postdate"+File.separator+formatted);
            postDate.createNewFile();
            // 그 생성일자를 이름으로 갖는 파일 만들기

        } catch(IOException e) {
            e.printStackTrace();
        }

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
            String printList = (i+1)+"\t\t\t"+list[i]+"\t\t";
            // 파일리스트를 글번호 글제목 형식에 맞게 printList에 저장.

            System.out.print(printList.replace(".txt",""));
            // 파일 확장자 제거.
        }

    }

    public void printPostDate(){
        // 파일 작성일자 출력하는 메소드.

        File checkPostList = new File(path+File.separator+"postdate");
        String[] list = checkPostList.list();
        // 디렉토리에있는 파일리스트 가져와서 배열에 저장.

        for(int i = 0; i<list.length; i++){
            String printList = list[i];
            System.out.println(printList);
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
