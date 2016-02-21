package com.grailsinaction

class User {
    String loginId
    String password
    String homepage
    Date dateCreated
    static hasOne = [ profile : Profile ]
    static hasMany = [ posts : Post, tags : Tag, following : User ]
    static constraints = {
        loginId size: 3..22, unique: true, nullable: false, validator: { login, user ->
            login != user.password }
        profile nullable: true
        password size: 6..8, nullable: false
        homepage url: true,
                nullable: true
        dateCreated min: new Date(), nullable: true
        tags()
        posts()
    }
}
