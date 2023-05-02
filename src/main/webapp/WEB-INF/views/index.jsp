<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CSPOP</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../assets/css/theme.min.css">
    <style>
        .right {
            float: right;
        }

        .fixed-top {
            background-color: #271F45;
        }

        header {
            display: flex;
            justify-content: space-between;
        }

        img {
            margin-top: 5%;
        }
    </style>
</head>
<%@include file="common/sessionController.jsp"%>
<body>
<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
</c:if>
<div class="header fixed-top border-3 border-top border-primary">
    <!-- navigation start -->
    <div class="container">
        <nav class="navbar navbar-default">
            <a class="navbar-brand" href="/">
                <img src="../../assets/images/fitness/cspop_logo.png" alt="" width="100em">
            </a>
            <div id="userCheck"></div>
        </nav>
    </div>
</div>
<!-- navigation close -->


<div class="bg-dark right-slant-shape pt-lg-16 py-12 pb-lg-0 ">
    <div class="container">
        <div class="row d-lg-flex align-items-center ">
            <div class="col-xxl-5 col-md-6 col-12">
                <div class="mb-0 mb-lg-8 ">
                    <h1 class=" text-white mb-3 display-4 ">
                        The End of Kyonggi univ.
                    </h1>
                    <p class="mb-6 lead">
                        배움의 마침표
                        <br>
                        졸업시스템
                    </p>
                    <a href="/notice/find?page=0&size=10" class="btn btn-primary me-3">Start Graduation</a>
                    </a>
                </div>
            </div>
            <div class="offset-xxl-2 col-xxl-5 offset-lg-1 col-lg-5 col-md-6 col-12 mb-n2 d-none d-md-block">
                <div class="position-relative z-1">
                    <img src="../../assets/images/fitness/aiBot.jpg" alt=""
                         class="img-fluid rounded w-100 border-bottom border-4 border-primary"/>
                    <div class="card-img-overlay d-flex flex-column justify-content-top p-4 ">
                        <h4 class="mb-0 h2">공학인증</h4>
                        <span class="font-12">당신의 공학 인증 요건을 확인하세요</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- link section -->
<div class="py-7 pt-lg-18 pb-lg-8">
    <div class="container">
        <div class="row">
            <div class="col-xl-12 col-lg-12 col-md-12 col-12">
                <div class="mb-6">
                    <h3 class="mb-0">
                        With Kyonggi
                    </h3>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="https://kutis.kyonggi.ac.kr/webkutis/view/indexWeb.jsp">KUTIS</a>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="https://lms.kyonggi.ac.kr/">LMS</a>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="http://swaig.kyonggi.ac.kr/main.kgu">SWAIG</a>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="http://www.kyonggi.ac.kr/">KYONGGI</a>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="http://cs.kyonggi.ac.kr:8080/webp/Index">CS-HOME</a>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-6">
                <div class="mb-4">
                    <a href="http://ai.kyonggi.ac.kr:8080/webp/Index">AI-HOME</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 출처관련한 부분은 추후 따로 분리합니다. -->
<a
        href="https://kr.freepik.com/free-vector/cloud-robotics-abstract-concept-illustration_12291072.htm#query=chatbot&position=4&from_view=keyword&track=sph">작가
    vectorjuice</a> 출처 Freepik
<!--  Jquery 가져오기 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<%@include file="common/commonJS.jsp"%>
</body>
</html>