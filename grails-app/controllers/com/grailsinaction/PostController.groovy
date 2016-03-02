package com.grailsinaction

import grails.web.RequestParameter

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

    def addPost(@RequestParameter('frm_id') String id,
                @RequestParameter('frm_content') String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }
}
