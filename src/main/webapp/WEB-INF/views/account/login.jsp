<%--
  Created by IntelliJ IDEA.
  User: hamhyeonjun
  Date: 2023/02/22
  Time: 0:51
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
    <link rel="stylesheet" href="../assets/css/theme.min.css">
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
                <h1 class="mb-2 text-center h3 ">로그인</h1>
                <br>
                <div class="mb-3">
                    <label for="id" class="form-label">학번</label><span id="warningID"></span>
                    <div class="input-group">
                        <input type="text" class="form-control" id="id" placeholder="학번을 입력해주세요."
                            value="" required maxlength="9">
                    </div>
                </div>
                <br>
                <div class="mb-3 ">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요." required="">
                </div>
                <br>
                <div class="d-grid">
                    <button class="btn btn-primary" type="submit" onclick="login()" id="submit-button" >
                        로그인
                    </button>
                </div>
                <div class="d-xxl-flex justify-content-end mt-4 ">
                    <span class="text-muted font-15 mb-0">
                        <a href="/api/signup">회원가입</a>&nbsp;
                        <a href="/api/passwordReset">비밀번호 찾기</a>
                    </span>
                </div>
  <%--          </form>--%>
            </div>
                <!-- End Form -->
            </div>
        </div>
    </div>
</div>

<%@include file="../common/commonJS.jsp" %>
<%--login JS--%>
<script src="../../../assets/js/login.js"></script>
</body>
</html>
