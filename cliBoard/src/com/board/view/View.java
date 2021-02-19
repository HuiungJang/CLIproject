package com.board.view;

import com.board.controller.PostController;

import java.util.Scanner;

public class View {
    private Scanner sc = new Scanner(System.in);
    private PostController pc = new PostController();
    public void mainView(){

        while(true){
            System.out.println("==================== 게시판 ====================");
            System.out.print("글 번호\t\t");
            System.out.print("글 제목");

            try {
                pc.postListPrint();
            }catch (NullPointerException e){
                // 처음 시작 시는 파일이 없으니까 Exception 방지
            }


            System.out.println();
            System.out.println("================================================");
            System.out.println("게시글을 보려면  \'post\'를 입력해주세요");
            System.out.println("게시글을 작성하려면  \'write\'를 입력해주세요");
            System.out.println("게시글을 삭제하려면  \'delete\'를 입력해주세요");
            System.out.print("어떤 것을 하시겠습니까? : ");
            String cho = sc.nextLine();

            switch (cho.toLowerCase()){ // 입력문자 소문자로 통일
                case "post" :
                    viewPost();
                    break;

                case "write" :
                    writePost();
                    break;

                case "delete" :
                    deletePost();
                    break;

                default:
                    System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
                    break;
            }

        }
    }

    public void viewPost() {
        System.out.println("==================== 글 조회 ====================");
        System.out.print("게시글을 보려면 게시물 제목을 입력해주세요 : ");
        String postTitle = sc.nextLine();
        pc.viewPost(postTitle);


    }

    public void writePost() {
        System.out.println("==================== 글 작성 ====================");
        System.out.print("게시글 제목을 입력해주세요 : ");
        String title = sc.nextLine();
        pc.createPost(title);
        // 입력받아서 게시글 생성.
        boolean stop = true;

        while(stop) {

            System.out.println("게시글 내용을 입력해주세요 : ");
            System.out.println("입력을 종료하려면 \' exit \'를 입력해주세요");
            String content = sc.nextLine();

            if(content.toLowerCase().equals("exit")){
                stop = false;
            }else{
                pc.createPostContent(title, content);
                // 입력받은 제목으로 파일 특정하고
                // 내용 추가하기.
            }

        }

    }

    public void deletePost() {
        System.out.println("==================== 글 삭제 ====================");
        System.out.println("관리자만 글을 삭제할 수 있습니다.");
        System.out.println("아이디 비밀번호를 입력해주세요.");
        System.out.print("아이디 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호 : ");
        String psw = sc.nextLine();

       if( pc.checkInfo(id, psw)){
           // 아이디 비밀번호가 맞으면
           System.out.print("삭제할 게시물 제목을 입력하세요 : ");
           String postTitle = sc.nextLine();
           System.out.println("정말 삭제하시겠습니까? (Y/N)");
           String check = sc.nextLine();

           if(check.toUpperCase().contains("Y")) {
            // 삭제를 한다면
              if( pc.deletePost(postTitle) ){
                  // 삭제가 됐다면
                  System.out.println("게시물이 삭제되었습니다.");
              }else
                  System.out.println("게시물이 존재하지 않습니다.");

           }else {
               System.out.println("게시물 삭제가 취소되었습니다.");
               System.out.println("게시판으로 돌아갑니다.");
               return;
           }


       }else{
           System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
           System.out.println("메인으로 돌아갑니다.");
           return;
       }
    }

}
