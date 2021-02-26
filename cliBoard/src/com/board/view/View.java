package com.board.view;

import com.board.controller.PostController;
import com.board.model.dao.Post;

import java.io.File;
import java.util.Scanner;

public class View {
    private Scanner sc = new Scanner(System.in);
    private PostController pc = new PostController();

    public void mainView(){

        File f = new File("adminInfo.txt");
        // 관리자 정보 파일 있는지 확인

            while (true) {

                if( !f.exists() ) {
                    // 없으면 관리자 정보저장하기
                    new Post().saveAdminInfo();
                }else {

                    System.out.println("==================== 게시판 ====================");
                    System.out.print("글 번호\t\t");
                    System.out.println("글 제목");

                    try {
                        pc.postListPrint();
                    } catch (NullPointerException e) {
                        // 처음 시작 시는 파일이 없으니까 Exception 방지
                    }

                    System.out.println();
                    System.out.println("================================================");
                    System.out.println("게시글을 보려면  'post'를 입력해주세요");
                    System.out.println("게시글을 작성하려면  'write'를 입력해주세요");
                    System.out.println("게시글을 삭제하려면  'delete'를 입력해주세요");
                    System.out.print("어떤 것을 하시겠습니까? : ");
                    String cho = sc.nextLine();

                    switch (cho.toLowerCase()) { // 입력문자 소문자로 통일
                        case "post":
                            viewPost();
                            break;

                        case "write":
                            writePost();
                            break;

                        case "delete":
                            deletePost();
                            break;

                        default:
                            System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
                            break;
                     }
                }
            }
        }

    public void viewPost() {
        System.out.println("==================== 글 조회 ====================");
        System.out.print("게시글을 보려면 게시물 제목을 입력해주세요 : ");
        String postTitle = sc.nextLine();

        System.out.print("글 내용 : ");
        pc.viewPost(postTitle);

        System.out.println("=================================================");

    }
    /*
    글 작성을 회원만 할 수 있도록 하기

    로그인이 필요할때
        1. 글작성할때

    필요한것
        회원가입
            1. 회원가입 형식
            2. 회원 가입한 정보를 저장할 파일과 이것을 실행할 메소드
            3. 회원가입시 중복아이디 걸러야함
        로그인
            1. 로그인 로그아웃 형식과 이를 어떻게 구현할지
            2. 로그인을 어떻게 유지할지
            3. 로그아웃을 어디서 해야하는지?

    구현 아이디어
        회원가입 흐름 개념
            1. 프로그램 실행시 회원정보 저장할 디렉토리 생성하기
            2. 형식은 아이디와 비밀번호만 입력받기
            3. 입력받은 정보를 Property 형식(key: 아이디, value: 비밀번호)으로 저장하고 파일이름을 아이디로하기
            4. 회원정보 모아둔 디렉토리의 파일 목록불러와서 일치하는 것이 있는지 확인
            --> 구현완료

        로그인 흐름 개념
            1. 글작성 메소드에서 조건에 따라 로그인 메소드 호출
            2. 회원정보 저장한 디렉토리 경로 가져오기
            3. 디렉토리의 파일 목록 가져오기
            4. 입력받은 아이디와 일치하는 파일이름을 가진 파일 열기
            5. 입력받은 아이디와 key값이 일치하는지 확인 -> true
                5-1 아이디가 일치한다면 입력받은 비밀번호와 value 값이 일치하는지 확인 -> true
                   5-1-1  비밀번호가 불일치하면 ->false
                5-2 아이디 불일치하면 -> false
            6. false가 나오면 "로그인 실패"
            7. true가 나오면 "로그인 성공"
            -->  구현실패
            -->  문제점
                 회원가입했을때 바로 로그인하면
                 키,벨류값이 남아있어서 로그인이 됨.
                 다시 로그인 하려면 키,벨류값이 초기화되어서 로그인이 안됨.

                 생각해본 해결방안
                 1. 파일이름을 아이디+비밀번호로 받고
                    로그인시 그걸로 검증하기
                    -> 구현완
         로그아웃 흐름 개념
            1. 입력 종료 -> 로그아웃(메인으로 돌아감)




   해야할 것
       - 글 작성 메소드 수정필요
            1. 글 작성 메소드 실행시 회원만 작성가능하다는 안내 출력
            2. 로그인 확인하기
             2-1  로그인 안했다면 회원가입 할껀지 물어보기
                2-1-1 한다면 회원가입 메소드로 이동
                2-1-2 안한다면 메인으로 이동

            2-2 로그인 했다면 글작성하기.

       - 구현아이디어대로 만들기

     */

    public void writePost() {
        System.out.println("==================== 글 작성 ====================");
        System.out.println("회원만 글을 작성 할 수 있습니다. ");
        // 아이디가 있는지 물어보고 없으면 회원가입하기
        System.out.println("로그인 하시겠습니까? (Y/N)");
        System.out.println("회원가입하시려면 'N'을 입력해주세요");
        String yn = sc.nextLine();

        if(yn.toUpperCase().equals("Y")) {
            // 로그인하도록하기
            if (signIn()) {
                // 로그인 성공했으면 게시물 작성
                System.out.print("게시글 제목을 입력해주세요 : ");
                String title = sc.nextLine();

                pc.createPost(title);
                // 입력받아서 게시글 생성.
                boolean stop = true;

                while (stop) {

                    System.out.print("게시글 내용을 입력해주세요 : ");
                    String content = sc.nextLine();

                    if (content.toLowerCase().equals("exit")) {
                        stop = false;
                    } else {
                        pc.createPostContent(title, content);
                        // 입력받은 제목으로 파일 특정하고
                        // 내용 추가하기.
                    }

                    System.out.println("입력을 종료하려면 ' exit '를 입력해주세요");
                    System.out.println("입력이 종료되면 자동으로 로그아웃 됩니다.");
                }
                System.out.println("=================================================");
            } else
                // 로그인실패면
                System.out.println("로그인에 실패했습니다. 다시 시도해주세요. ");
        }else if(yn.toUpperCase().equals("N")) {
            signUp();
        }else
            System.out.println("잘못 입력하셨습니다.");
    }

    public void deletePost() {
        System.out.println("==================== 글 삭제 ====================");
        System.out.println("관리자만 글을 삭제할 수 있습니다.");
        System.out.println("관리자 아이디와 비밀번호를 입력해주세요.");
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

        System.out.println("=================================================");

    }

    public void signUp(){
        System.out.println("=================== 회원 가입 ===================");
        System.out.print("사용 할 아이디를 입력해주세요 : ");
        String id = sc.nextLine();


        System.out.print("사용 할 비밀번호를 입력해주세요 : ");
        String psw = sc.nextLine();

       if( pc.signUp(id, psw)){
           // 생성된 회원 정보파일에 아이디, 비밀번호 저장.
           System.out.println("회원가입이 완료되었습니다.");
       }else
           System.out.println("이미 사용중인 아이디입니다. 다시 회원가입을 해주세요.");
        System.out.println("=================================================");
    }


    public boolean signIn(){
        System.out.println("===================== 로그인 =====================");
        System.out.print("아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String psw = sc.nextLine();

        if(pc.signIn(id,psw)){
            System.out.println("로그인 되었습니다.");
            return true;
        }

        System.out.println("=================================================");

        return false;
    }

}
