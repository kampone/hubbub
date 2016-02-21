package com.grailsinaction

class PostController {
    static scaffold = true
    static final NOT_FOUND = 404
    def timeline() {
        def user = User.findByLoginId(params.id)
        if(!user){
            response.sendError(NOT_FOUND)
        }else {
            [user : User]
        }
    }
}
