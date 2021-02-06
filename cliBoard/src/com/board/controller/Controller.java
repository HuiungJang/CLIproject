package com.board.controller;

import java.io.File;
import java.io.IOException;

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

    public void createPostContent(String content){

    }

}
