package com.grailsinaction



import spock.lang.*

/**
 *
 */
class PostIntegrationSpec extends Specification {

    def "Adding post to user links post to user"(){
        given: "A brand new user"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)

        when: "Several posts are added to the user"
        user.addToPosts(new Post(content: "First post... w00t!", dateCreated: new Date()))
        user.addToPosts(new Post(content: "Second post.. ", dateCreated: new Date()))
        user.addToPosts(new Post(content: "Third post"))

        then: "User has a list of posts attached"
        3 == User.get(user.id).posts.size()
    }

    def "Ensure posts linked to a User can be retrieved"(){

        given: "A user with several posts"
        def user = new User(loginId: 'joe', password: 'secret')
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))
        user.save(failOnError: true)

        when: "The user is recieved by id"
        def foundUser = User.get(user.id)
        def sortedPostsContent = foundUser.posts.collect{
            it.content
        }.sort()

        then: "The posts appear on retrieved user"
        sortedPostsContent == ['First', 'Second', 'Third']
    }

    def "Exercise tagging several posts with various tags"(){
        given: "A user with a set of tags"
        def user = new User(loginId: 'joe', password: 'secret')
        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGrails)
        user.addToTags(tagGroovy)
        user.save(failOnError: true)

        when: "The user tags two fresh posts"
        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)

        def bothPost = new Post(content: "A groovy and grails post")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGrails)
        bothPost.addToTags(tagGroovy)

        then:
        user.tags*.name.sort() == ['grails', 'groovy']
        1 == groovyPost.tags.size()
        2 == bothPost.tags.size()
    }
}
