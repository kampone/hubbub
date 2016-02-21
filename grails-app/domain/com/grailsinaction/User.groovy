package com.grailsinaction

class User {
    String loginId
    String password
    String homepage
    Date dateCreated
    static hasOne = [ profile : Profile ]
    static hasMany = [ posts : Post, tags : Tag, following : User ]
    static constraints = {
        loginId unique: true, nullable: false
        profile nullable: true
        password size: 4..8, nullable: false
        homepage url: true,
                nullable: true
        dateCreated nullable: true
        tags()
        posts()
    }

    @Override
    String toString() {
        return loginId;
    }
}
