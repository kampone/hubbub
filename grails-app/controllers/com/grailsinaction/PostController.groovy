package com.grailsinaction

class PostController {
    static scaffold = true
    static final NOT_FOUND = 404
    static final DEFAULT_USER = "chuck_norris"

    def index() {
        if (!params.id) {
            params.id = DEFAULT_USER
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline() {
        def user = User.findByLoginId(params.id)
        if(!user){
            response.sendError(NOT_FOUND)
        }else {
            [user : user]
        }
    }

    def addPost() {
        def user = User.findByLoginId(params.id)
        if(user){
            def post = new Post(params)
            user.addToPosts(post)
            if (user.save()){
                flash.message = "Successfully created Post"
            } else {
                flash.message = "Invalid or empty post"
            }
        } else {
            flash.message = "Invalid User Id"
        }
        redirect(action: timeline(), id: params.id)
    }
}
