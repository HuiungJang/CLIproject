package com.board.controller;

import com.board.model.dao.Post;

public class PostController {
    private Post p = new Post();

    public void createPost(String title){
       p.createPost(title); // 게시글 파일 만들기
       p.createPostDate(title); // 게시글 생성일자 저장 파일만들기
    }

    public void createPostContent(String title,String content){
        p.createPostContent(title,content);
    }

    public void viewPost(String postTitle){
        p.viewPost(postTitle);
    }

    public boolean checkInfo(String id, String psw){
       return  p.checkInfo(id,psw);
    }

    public boolean deletePost(String postTitle){
       return p.deletePost(postTitle);
    }

    public void postListPrint(){
        p.postListPrint();
    }

    public void printPostDate(){
        p.printPostDate();
    }


    public boolean signIn(String id, String psw){
        return p.signIn(id,psw);
    }

    public boolean signUp(String id, String psw){
        return p.signUp(id, psw);
    }

}

