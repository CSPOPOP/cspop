<%--
  Created by IntelliJ IDEA.
  User: hamhyeonjun
  Date: 2023/02/22
  Time: 1:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <!-- 부트스트랩 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- 부트스트랩 테이블 -->
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.15.5/dist/bootstrap-table.min.css">
    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../../assets/css/theme.min.css">
    <style>
        .fixed-top {
            background-color: #672EBB;
        }

        img {
            margin-top: 5%;
        }
    </style>

</head>

<%@include file="../common/sessionController.jsp"%>
<body>
<%@include file="../common/header.jsp"%>
<section class="page-start">
    <!-- pageheader section -->
    <div class="bg-shape bg-secondary">
        <div class="container">
            <div class="row">
                <div class="offset-xl-1 col-xl-10 col-lg-12 col-md-12 col-12">
                    <div class="pt-lg-18 pb-lg-16 py-12 ">
                        <div class="row align-items-center">
                            <div class="col-lg-12 col-md-12 col-12 mb-3">
                                <!-- breadcrumb -->
                                <div class="custom-breadcrumb">
                                    <ol class="breadcrumb mb-2">
                                        <li class="breadcrumb-item text-white">
                                            <a href="/api/home">Home</a>
                                        </li>
                                        <li class="breadcrumb-item text-white ">
                                            <a href="/notice/find?page=0&size=10">공지사항</a>
                                        </li>
                                        <li class="breadcrumb-item active " aria-current="page">
                                            글쓰기
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <h1 class="h2 text-white mb-2">공지사항</h1>
                                <p class="text-white-50 lead">
                                    글쓰기
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="pb-10 mt-n10">
        <div class="container">
            <div class="row">
                <div class="offset-lg-1 col-lg-10 col-md-12 col-12">
                    <div class="card">
                        <div class="card-body p-4 p-lg-7">
                            <form id="myForm" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <spring:hasBindErrors name="noticeBoardRequestDto">
                                        <c:if test="${errors.hasFieldErrors('title') }">
                                            <span style="color: red">${errors.getFieldError( 'title' ).defaultMessage }</span>
                                        </c:if>
                                    </spring:hasBindErrors>
                                    <input type="text" class="form-control" id="post_title" name="title" placeholder="제목 :">
                                    <spring:hasBindErrors name="noticeBoardRequestDto">
                                        <c:if test="${errors.hasFieldErrors('text') }">
                                            <span style="color: red">${errors.getFieldError( 'text' ).defaultMessage }</span>
                                        </c:if>
                                    </spring:hasBindErrors>
                                    <textarea id="editor" name="text">${data}</textarea>
                                    <input id="inputFile" type="file" name="files" multiple>
                                    <button type="submit" class="btn btn-default">쓰기</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../common/commonJS.jsp" %>
<script src="../../../assets/js/ckeditor/ckeditor.js"></script>
<script>
<%--    api/notice/form--%>
    CKEDITOR.replace('editor',{
        height: 500,
        allowedContent: true, // 허용되는 컨텐츠 설정
    })
</script>
</body>

</html>
