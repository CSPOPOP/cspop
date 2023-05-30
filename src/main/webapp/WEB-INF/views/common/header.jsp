<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- header -->
<c:set var="userId" value="<%= userId %>" scope="page" />
<div class="header fixed-top border-3 border-top border-primary border-sm">
    <!-- navigation start -->
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-default">
            <a class="navbar-brand" href="/api/home">
                <img src="../../../../assets/images/cspop_logo.png" alt="" width="100em">
            </a>
            <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbar-default" aria-controls="navbar-default"
                aria-label="Toggle navigation">
                <span class="icon-bar top-bar mt-0"></span>
                <span class="icon-bar middle-bar"></span>
                <span class="icon-bar bottom-bar"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbar-default">
                <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbar-default" aria-controls="navbar-default"
                    aria-label="Toggle navigation">
                    <i class="fas fa-times"></i>
                </button>
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/notice/find?page=0&size=10" data-bs-display="static">
                            공지사항
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/graduation/guide" data-bs-display="static">
                            안내 및 내규
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="/api/graduation/schedule" data-bs-display="static">
                            진행일정
                        </a>
                    </li>
                    <c:if test="${fn:contains(userId, 'admin')}">
                        <li class="nav-item">
                            <a class="nav-link" href="/api/graduation/graduate_management?page=0&size=10" data-bs-display="static">
                                졸업자 조회
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${fn:contains(userId, 'admin')}">
                        <li class="nav-item">
                            <a class="nav-link" href="/api/graduation/certification_management?page=0&size=10" data-bs-display="static">
                                공학인증 조회
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${not fn:contains(userId, 'admin')}">
                        <li class="nav-item">
                            <a class="nav-link " href="/api/userStatus" data-bs-display="static">
                                나의 졸업 현황
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${fn:contains(userId, 'admin')}">
                        <li class="nav-item">
                            <a class="nav-link " href="/api/graduation/application_management" data-bs-display="static">
                                졸업 논문 관리
                            </a>
                        </li>
                    </c:if>
                </ul>
                <div id="userCheck"></div>
            </div>
        </nav>
    </div>
</div>
<!--  Jquery 가져오기 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>

<%--account js include--%>
<script src="../../../../assets/js/signUp.js"></script>

<!-- navigation close -->
