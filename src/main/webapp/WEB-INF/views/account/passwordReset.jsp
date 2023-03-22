<!--<%@ page contentType="text/html;charset=UTF-8" language="java" %>-->
<!DOCTYPE html>
<html lang="en">

<head>
    <title>CSPOP : 비밀번호 찾기</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="../../../assets/libs/jquery/dist/jquery.min.js"></script>

    <!-- Libs CSS -->
    <link rel="stylesheet" href="../../../assets/libs/ion-rangeslider/css/ion.rangeSlider.min.css">
    <link rel="stylesheet" href="../../../assets/libs/litepicker/dist/css/litepicker.css">
    <link rel="stylesheet" href="../../../assets/libs/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="../../../assets/libs/magnific-popup/dist/magnific-popup.css">


    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../../assets/css/theme.min.css">
    <style>
        body{
            width:110%; 
        }
    </style>
</head>

<body class="bg-light">
<!-- content section -->

<div class="d-flex align-items-center position-relative vh-100">


    <div class="col-lg-5 col-xl-4 d-none d-lg-flex border-top border-primary border-4 align-items-center vh-100 px-0"
         style="background-image: url(../assets/images/register-img.jpg); background-position:13% 50%;">
    </div>
    <div class="container">
        <div class="row g-0">
            <div class="col-md-8 col-lg-7 col-xl-6 offset-md-2 offset-lg-2 offset-xl-3 space-top-3 space-lg-0">
                <!-- Form -->
                <div class="bg-white p-4 p-xl-6 p-xxl-8 p-lg-4 rounded-3 border">
<%--                    <form id="form" action="/api/home" method="get">--%>
                        <h1 class="mb-2 text-center h3 ">비밀번호 찾기</h1>
                        <br>
                        <div class="mb-3">
                            <div class="">
                                <label for="id" class="form-label">학번</label><span id="warningID"></span>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="id" placeholder="학번을 입력해주세요."
                                           value="" required maxlength="9">
                                    <button id="btnIdCheck" class="btn btn-primary" onclick="getStudentNumber(this)">확인</button>
                                </div>
                            </div>
                        </div>
                        <div class="mb-3">
                            <div class="">
                                <label for="questionPw" class="form-label">비밀번호 찾기 질문</label><br>
                                <select class="form-select" id="questionPw" required></select>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="answerPw" placeholder="답을 입력해 주세요."
                                           value="" required maxlength="9">
                                    <button id="btnAnswerCheck" class="btn btn-primary" onclick="passwordAnswer(this)">확인</button>
                                    <!--//아이디명 pwAnswer로 변경하였습니다-->
                                </div>
                            </div>
                        </div>
                        <!--<br>-->
                        <div class="mb-3 ">
                            <label for="password" class="form-label">새로운 비밀번호<span class="text-danger">*</span></label>
                            <input type="password" id="password" class="form-control" placeholder="8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요." required="">
                        </div>
                        <div class="mb-3 ">
                            <label for="password" class="form-label">비밀번호 확인<span
                                    class="text-danger">*</span></label>
                            <input type="password" id="password2" class="form-control" placeholder="비밀번호를 확인해주세요."
                                   required="">
                        </div>
                        <br>

                        <div class="d-grid">
                            <button class="btn btn-primary" type="submit" onclick="resetPassword()" id="submit-button" >
                                비밀번호 변경
                            </button>
                        </div>
                        <div class="d-xxl-flex justify-content-between mt-4 ">
                            <span class="text-muted font-14 mb-0">
                                <a href="/api/signup">회원가입</a>
                            </span>
                            <span>&nbsp;</span>
                            <span class="font-14 mb-0">
                                <a href="/api/login">로그인</a>
                            </span>
                        </div>
 <%--                    </form>--%>
                </div>
                <!-- End Form -->
            </div>
        </div>
    </div>

</div>
<!--  Jquery 가져오기 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>

<%--passwordReset js include--%>
<script src="../../../assets/js/passwordReset.js"></script>
</body>

</html>
