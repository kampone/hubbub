package com.grailsinaction

import grails.web.RequestParameter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

class PostController {
    static defaultAction = "timeline"
    static scaffold = true
    static final NOT_FOUND = 404
    static final DEFAULT_USER = "admin"

    def postService

    def index() {
        if (!params.id) {
            params.id = DEFAULT_USER
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline(String id) {
        def user = User.findByLoginId(id)
        if(!user){
            log.error('User ${id} is not found')
            response.sendError(NOT_FOUND)
        }else {
            [user : user]
        }
    }

    def addPost(@RequestParameter('id') String id,
                @RequestParameter('content') String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }

    def delete(Long id){
        def post = Post.findById(id)
        if (post?.delete()){
            redirect(action: 'index')
        }else {
            redirect(action: 'index')
            flash.message = "Can't delete post:" + post?.toString()
        }
    }
}
