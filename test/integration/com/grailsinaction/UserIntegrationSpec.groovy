package com.grailsinaction



import spock.lang.*

/**
 *
 */
class UserIntegrationSpec extends Specification {
    def "Saving our first user to the data base"() {
        given: "A brand new user"
        def joe = new User(loginId: 'joe', password: 'secret', homepage: "http://www.grailsinaction.com")

        when: "the user is saved"
        joe.save()

        then: "it saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    def "Updating a saved user changes its properties"(){
        given: "An existing user"
        def existingUser = new User(loginId: 'joe', password: 'secret', homepage: "http://www.grailsinaction.com")

        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = 'sesame'
        foundUser.save(failOnError: true)

        then: "The changes is reflected in the database"
        User.get(existingUser.id).password == 'sesame'

    }

    def "Deleting the existing user removes it from database"(){

        given: "An existing user"
        def user = new User(loginId: 'joe', password: 'secret', homepage: "http://www.grailsinaction.com")
        user.save(failOnError: true)

        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true)

        then: "The user is removed from database"
        !User.exists(foundUser.id)

    }

    def "Saving a user with invalid properties cause an error"() {
        given: "A user which fails several field validations"
        def user = new User(loginId: 'joe', password: 'tiny', homepage: 'not-a-url')

        when: "The user is validated"
        user.validate()

        then:
        user.hasErrors()

        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny" == user.errors.getFieldError("password").rejectedValue
        "url.invalid" == user.errors.getFieldError("homepage").code
        !user.errors.getFieldError("loginId")
    }

    def "Ensure a user can follow other users"(){

        given: "A set of baseline users"
        def joe = new User(loginId: 'joe', password: 'password').save()
        def jane = new User(loginId: 'jane', password: 'password').save()
        def jill = new User(loginId: 'jill', password: 'password').save()

        when: "Joe follows Jane & Jill, and Jill follows Jane"
        joe.addToFollowing(jane)
        joe.addToFollowing(jill)
        jill.addToFollowing(jane)

        then: "Follower counts should match following people"
        2 == joe.following.size()
        1 == jill.following.size()
    }
}
