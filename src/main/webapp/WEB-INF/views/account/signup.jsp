`<%--
  Created by IntelliJ IDEA.
  User: hamhyeonjun
  Date: 2023/02/22
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>CSPOP</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Libs CSS -->
    <link rel="stylesheet" href="../../../assets/libs/ion-rangeslider/css/ion.rangeSlider.min.css">
    <link rel="stylesheet" href="../../../assets/libs/litepicker/dist/css/litepicker.css">
    <link rel="stylesheet" href="../../../assets/libs/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="../../../assets/libs/magnific-popup/dist/magnific-popup.css">


    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../../assets/css/theme.min.css">
</head>

<body class="bg-light">
<!-- content section -->

<div class="d-flex align-items-center position-relative min-vh-100">
    <div class="container">
        <div class="row g-0">
            <div class="col-md-8 col-lg-7 col-xl-6 offset-md-2 offset-lg-2 offset-xl-3 space-top-3 space-lg-0">
            <a href="/api/home" class="d-flex justify-content-center align-items-center">
                <img src="../../../../assets/images/fitness/cspop_logo.png" alt="" width="130em">
            </a>
            <br>
            <!-- Form -->
            <div class="bg-white p-4 p-xl-6 p-xxl-8 p-lg-4 rounded-3 border" style="max-width: 500px; margin: 0 auto;">
<%--            <form id="form" action="/api/home" method="get">--%>
                <h1 class="mb-2 text-center h3 ">회원가입</h1>
                <br>
                <div class="mb-3">
                    <div class="">
                        <label for="id" class="form-label">학번</label><span id="warningID"></span>
                        <div class="input-group">
                            <input type="text" class="form-control" id="id" placeholder="학번을 입력해주세요."
                                value="" required maxlength="9">
                            <button class="btn btn-primary" onclick=checkId()>중복확인</button>
                        </div>
                    </div>
                </div>
                <div class="mb-3 ">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" id="password" class="form-control" placeholder="8~16자 영문,특수문자를 사용하세요."
                        required="">
                </div>
                <div class="mb-3 ">
                    <label for="password" class="form-label">비밀번호 확인</label>
                    <input type="password" id="confirmPassword" class="form-control" placeholder="비밀번호를 확인해주세요."
                        required="">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">이름</label>
                    <input type="text" id="name" class="form-control" placeholder="이름을 입력해주세요." required="">
                </div>
                <div class="md-3">
                    <label for="gender" class="form-label">성별</label>
                    <div id="gender">
                        <div class="form-check">
                            <input id="male" name="gender" type="radio" class="form-check-input" value="남"
                                checked required>
                            <label class="form-check-label" for="male">남</label>
                        </div>
                        <div class="form-check">
                            <input id="female" name="gender" type="radio" class="form-check-input" value="여"
                                required>
                            <label class="form-check-label" for="female">여</label>
                        </div>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">E-mail</label><span id="warningEmail"></span>
                    <div class="input-group">
                        <input type="email" class="form-control" id="email" placeholder="E-mail을 입력해주세요.">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">휴대폰 번호</label>
                    <input type="text" class="form-control" id="phone" placeholder="휴대폰 번호를 입력해주세요."
                        required maxlength="13"/>
                </div>
                <div class="mb-3">
                    <label for="major" class="form-label">학과</label>
                    <select class="form-select" id="major" required></select>
                    <div class="invalid-feedback">
                        학과를 선택해 주세요
                    </div>
                </div>
                <div class="col-12">
                    <label for="questionPw" class="form-label">비밀번호 찾기 질문</label>
                    <select class="form-select" id="questionPw" required></select>
                    <div class="invalid-feedback">
                        비밀번호 찾기 질문을 선택하시고, 답변을 입력해주세요.
                    </div>
                </div>
                <div class="col-12">
                    <label for="answerPw" class="form-label"></label>
                    <input type="text" class="form-control" id="answerPw" placeholder="답변을 입력해주세요.">
                </div>
                <br>
                <div class="d-grid">
                    <button class="btn btn-primary" type="submit" onclick="signUp(event)" id="submit-button" >
                        회원가입
                    </button>
                </div>
                <div class="d-xxl-flex justify-content-end mt-4 ">
                    <span class="text-muted font-15 mb-0 ">
                        이미 회원입니까? <a href="/api/login">로그인</a>
                    </span>
                </div>
<%--            </form>--%>
            </div>
            <!-- End Form -->
            </div>
        </div>
    </div>
</div>
<!--  Jquery 가져오기 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<%--account js include--%>
<script src="../../../assets/js/signUp.js"></script>
<%@include file="../common/commonJS.jsp"%>
</body>
</html>
