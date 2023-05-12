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
<c:if test="${errorMessage eq true}">
    <script>
        alert("검색 결과가 없습니다.")
    </script>
</c:if>
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
                                        <li class="breadcrumb-item active " aria-current="page">
                                            공지사항
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <h1 class="h2 text-white mb-2">공지사항</h1>
                                <p class="text-white-50 lead">
                                    졸업 논문 공지사항
                                </p>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <form action="/api/notice/search?page=10&size=10" method="get">
                                        <span class="position-absolute ps-3 pt-1 mt-1">
                                            <i class="fe fe-search"></i>
                                        </span>
                                    <input name="word" type="search" class="form-control ps-6 mb-2 border-0"
                                           placeholder="search"/>
                                </form>
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
                            <div>
                                <div class="card table-responsive">
                                    <table id="table" class="table mb-0" border-color="black">
                                        <thead class="table-light">
                                        <tr class="text-center">
                                            <th>번호</th>
                                            <th>제목</th>
                                            <th>글쓴이</th>
                                            <th>작성일</th>
                                            <th>조회수</th>
                                        </tr>
                                        <c:forEach items="${allNoticeBoard.content}" var="noticeBoard">
                                            <tr class="text-center" data-id=${noticeBoard.id}>
                                                <c:if test="${noticeBoard.fixed eq true}">
                                                    <td class="text-danger">[공지]</td>
                                                </c:if>
                                                <c:if test="${noticeBoard.fixed ne true}">
                                                    <td>${noticeBoard.id}</td>
                                                </c:if>
                                                <td><a href="/notice/view/detail/${noticeBoard.id}"
                                                       class="text-black">${noticeBoard.title}</a></td>
                                                <td>관리자</td>
                                                <td>${noticeBoard.createdDate}</td>
                                                <td>${noticeBoard.views}</td>
                                            </tr>
                                        </c:forEach>
                                        </thead>
                                    </table>
                                </div>
                                <c:if test="${fn:contains(userId, 'admin')}">
                                    <a href="/api/graduation/form">
                                        <button class="btn btn-primary btn-sm float-right">글쓰기</button>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <!-- 페이징 영역 시작 -->
                        <div class="text-xs-center">
                            <ul class="pagination justify-content-center">
                                <!-- 이전 -->
                                <c:choose>
                                    <c:when test="${allNoticeBoard.first}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/notice/find?page=0&size=10">처음</a></li>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/notice/find?page=${allNoticeBoard.number-1}&size=10">이전</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <!-- 페이지 그룹 -->
                                <c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
                                    <c:choose>
                                        <c:when test="${allNoticeBoard.pageable.pageNumber+1 == i}">
                                            <li class="page-item disabled"><a class="page-link"
                                                                              href="/notice/find?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                href="/notice/find?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <!-- 다음 -->
                                <c:choose>
                                    <c:when test="${allNoticeBoard.last}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/notice/find?page=${allNoticeBoard.number+1}&size=10">다음</a>
                                        </li>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/notice/find?page=${allNoticeBoard.totalPages-1}&size=10">마지막</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                        <!-- 페이징 영역 끝 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
<script src="../../../../assets/js/detailPage.js">

</script>
</body>
</html>
