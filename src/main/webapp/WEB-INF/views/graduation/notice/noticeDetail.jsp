<%--
  Created by IntelliJ IDEA.
  User: hamhyeonjun
  Date: 2023/02/22
  Time: 1:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CSPOP</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Libs CSS -->
    <link rel="stylesheet" href="../../../../assets/libs/ion-rangeslider/css/ion.rangeSlider.min.css">
    <link rel="stylesheet" href="../../../../assets/libs/litepicker/dist/css/litepicker.css">
    <link rel="stylesheet" href="../../../../assets/libs/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="../../../../assets/libs/magnific-popup/dist/magnific-popup.css">
    <!-- 부트스트랩 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- 부트스트랩 테이블 -->
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.15.5/dist/bootstrap-table.min.css">
    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../../../assets/css/theme.min.css">
    <style>
        .fixed-top {
            background-color: #672EBB;
        }

        img {
            margin-top: 5%;
        }
    </style>
</head>
<%@include file="../../common/sessionController.jsp" %>
<body>
<%@include file="../../common/header.jsp" %>
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
                                        <li class="breadcrumb-item active">
                                            <a href="/notice/find?page=0&size=10">공지사항</a>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <h1 class="h2 text-white mb-2">${detailView.title}</h1>
                                <p class="text-white-50 lead">
                                    공지사항 세부
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
                            <h3 class="card-title">${detailView.title}</h3>
                            <div class="card-footer">
                                <div class="row">
                                    <div class="col-7"></div>
                                    <div class="col-2 text-end"><small>조회수: ${detailView.views}</small></div>
                                    <div class="col-3 text-end"><small>작성날짜: ${detailView.createdDate}</small></div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-12">
                                        <div class="card-text">${detailView.text}</div>

                                    </div>

                                </div>
                                <div class="row mt-2">
                                    <small>첨부파일:</small>
                                    <ul class="list-unstyled mb-0">
                                        <c:forEach items="${detailView.files}" var="file">
                                            <li><a href="/attach/${detailView.id}/${file}">${file}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <%--버튼 Start--%>
                                <div style="text-align: left; float: left; margin-top: 5px">
                                    <a href="/notice/find?page=0&size=10" class="btn btn-primary text-white">목록</a>
                                </div>
                                <div style="text-align: right; float: right; margin-top: 5px">
                                    <c:if test="${fn:contains(userId, 'admin')}">
                                        <a href="/api/graduation/modifyForm/${detailView.id}" class="btn btn-primary text-white">수정</a>
                                        <c:if test="${detailView.fixed eq true}">
                                            <button id="fixNotice" class="btn btn-success">해제</button>
                                        </c:if>
                                        <c:if test="${detailView.fixed ne true}">
                                            <button id="fixNotice" class="btn btn-success">고정</button>
                                        </c:if>
                                        <button id="deleteNotice" class="btn btn-danger">삭제</button>
                                    </c:if>
                                </div>
                            <%--버튼 End--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
<script src="../../../../assets/js/detailPage.js"></script>
<script>
    var noticeId ={
      "noticeBoardId" : ${detailView.id}
    }

    $(()=>{
        $('#fixNotice').on('click', () => { // 글을 고정,해제하는 기능
            console.log(typeof noticeId)
            $.ajax({
                url: "/api/notice/fix",
                type: "post",
                data: JSON.stringify(noticeId),
                contentType: "application/json; charset=utf-8",
                success: () => {
                    alert("설정 완료");
                    window.location.reload();
                }, error: (res) => {
                    alert(res.responseJSON.errorMessage);
                }
            })
        });
    })

    $(()=>{
        $('#deleteNotice').on('click', ()=> { // 글을 삭제하는 기능
            $.ajax({
                url: "/api/notice/delete",
                type: "post",
                data: JSON.stringify(noticeId),
                contentType: "application/json; charset=utf-8",
                success: () => {
                    alert("삭제 완료");
                    window.location.replace("/notice/find?page=0&size=10");
                }, error: (res) => {
                    alert(res.responseJSON.errorMessage);
                }
            })
        });
    })


</script>
</body>
</html>
