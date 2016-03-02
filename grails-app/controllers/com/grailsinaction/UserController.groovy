package com.grailsinaction

class UserController {
    static scaffold = true

    def search() {}

    def results(String loginId) {
        def users = User.where {
            loginId =~ loginId
        }.list()
        return [ users: users, term: params.loginId, totalUsers: User.count() ]
    }

    def update() {
        def user = session.user?.attach()
        if (user){
            user.properties['email', 'fullName'] = params
            if(user.save()){
                flash.message = "Successfully update user"
            } else {
                flash.message = "Failed to update user"
            }
            [user:user]
        } else {
            response.sendError(404)
        }
    }

    def register() {
        if (request.method == "POST"){
            def user = new User(params)
            if (user.validate()){
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else{
                flash.message = "Error Registering User"
                return [user:user]
            }
        }
    }

    def register2(UserRegistrationCommand urc){
        if (urc.hasErrors()) {
            render view: "register", model: [user : urc]
        }else {
            def user = new User(urc.properties)
            user.profile = new Profile(urc.properties)
            if (user.validate() && user.save()){
                "Welcome aboard, ${urc.fullName ?: urc.loginId}"
                redirect(uri: '/')
            } else {
                return [user : urc]
            }
        }
    }

}
