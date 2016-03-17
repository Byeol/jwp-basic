<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${question.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="" class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/profile?userId=${question.writer}" class="article-author-name">${question.writer}</a>
                            <a href="/questions/${question.questionId}" class="article-header-time" title="퍼머링크">
                                <fmt:formatDate value="${question.createdDate}" pattern="yyyy-MM-dd HH:mm"/>
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <div class="article-doc">
                        <p>${question.contents}</p>
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="/questions/${question.questionId}/form">수정</a>
                            </li>
                            <li>
                                <form class="form-delete" action="/questions/${question.questionId}" method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">
                            <c:forEach items="${answers}" var="answer" varStatus="status">
                            <article class="article" id="answer-${answer.answerId}">
                                <div class="article-header">
                                    <div class="article-header-thumb">
                                        <img src="" class="article-author-thumb" alt="">
                                    </div>
                                    <div class="article-header-text">
                                        <a href="/users/profile?userId=${answer.writer}" class="article-author-name">${answer.writer}</a>
                                        <a href="#answer-${answer.answerId}" class="article-header-time" title="퍼머링크">
                                            <fmt:formatDate value="${answer.createdDate}" pattern="yyyy-MM-dd HH:mm"/>
                                        </a>
                                    </div>
                                </div>
                                <div class="article-doc comment-doc">
                                    <p>${answer.contents}</p>
                                </div>
                                <div class="article-util">
                                    <ul class="article-util-list">
                                        <li>
                                            <a class="link-modify-article" href="/questions/${question.questionId}/answers/${answer.answerId}/form">수정</a>
                                        </li>
                                        <li>
                                            <form class="form-delete" action="/questions/${question.questionId}/answers/${answer.answerId}" method="POST">
                                                <input type="hidden" name="_method" value="DELETE">
                                                <button type="submit" class="link-delete-article">삭제</button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </article>
                            </c:forEach>

                            <form class="submit-write">
                                <div class="form-group" style="padding:14px;">
                                    <textarea class="form-control" placeholder="Update your status"></textarea>
                                </div>
                                <button class="btn btn-success pull-right" type="button">Post</button>
                                <div class="clearfix" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>
