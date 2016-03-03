<%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 21.02.2016
  Time: 18:31:PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        Timeline for ${ user.profile ? user.profile.fullName : user.loginId }
    </title>
    <meta name="layout" content="main">
</head>

<body>
    <h1>Timeline for ${ user.profile ? user.profile.fullName : user.loginId }</h1>
    <div id="newPost">
        <h3>
            What is ${user.profile.fullName} hacking on right now?
        </h3>
        <p>
            <g:form action="addPost" id="${params.id}">
                <g:textArea name="content" id="postContent" rows="3" cols="50" value="Post"/>
                <g:submitButton name="post" value="Post"/>
            </g:form>
        </p>
    </div>
    <div id="allPosts">
        <g:each in="${user.posts}" var="post">
                <h6 align="right">
                    <g:link action="delete" params="[id: post.id]" >x</g:link>
                </h6>
            <div class="postEntry">
                ${post.content}
            </div>
            <div class="postDate">
                ${post.dateCreated}
            </div>
        </g:each>
    </div>
</body>
</html>