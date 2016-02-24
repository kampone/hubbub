package com.grailsinaction

class PostController {
    static defaultAction = "timeline"
    static scaffold = true
    static final NOT_FOUND = 404
    static final DEFAULT_USER = "chuck_norris"

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
            response.sendError(NOT_FOUND)
        }else {
            [user : user]
        }
    }

    def addPost(String id, String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }
}
