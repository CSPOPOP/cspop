<!DOCTYPE html>
<html>
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
        ul {
            list-style: none;
        }
        li {
            float: left;
        }
    </style>
    <script src="../../../../assets/js/ckeditor/ckeditor.js"></script>
    <script src="../../../../assets/js/graduation.js"></script>
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
                                            졸업자 조회
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <h1 class="h2 text-white mb-2">졸업자 조회</h1>
                                <p class="text-white-50 lead">
                                    졸업자 현황
                                </p>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <form action="/api/graduation/allStep/search?page=0&size=10" method="get">
                                    <span class="position-absolute ps-3 pt-1 mt-1">
                                        <i class="fe fe-search"></i>
                                    </span>
                                    <input name="word" type="search" class="form-control ps-6 mb-2 border-0"
                                           placeholder="search"/>
                                </form>
                                <br>
                                <%@include file="graduation_menu.jsp" %>
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
                                        <thead class="table-dark">
                                        <tr style="text-align: center">
                                            <th data-field="no">
                                                <div class="th-inner sortable both">#</div>
                                            </th>
                                            <th data-field="studentId">
                                                <div class="th-inner sortable both">학번</div>
                                            </th>
                                            <th data-field="studentName">
                                                <div class="th-inner sortable both">학생 이름</div>
                                            </th>
                                            <th data-field="professorName">
                                                <div class="th-inner sortable both">교수 이름</div>
                                            </th>
                                            <th data-field="graduationDate">
                                                <div class="th-inner sortable both">학생 졸업날짜</div>
                                            </th>

                                            <th data-field="step">
                                                <div class="th-inner sortable both">단계</div>
                                            </th>
                                            <th data-field="state">
                                                <div class="th-inner sortable both">상태</div>
                                            </th>
                                            <th data-field="otherQualifications">
                                                <div class="th-inner sortable both">기타 자격</div>
                                            </th>
                                            <th data-field="capstoneCompletion">
                                                <div class="th-inner sortable both">캡스톤 이수</div>
                                            </th>
                                        </tr>
                                        </thead>
                                        <c:forEach var="data" items="${graduator.content}" varStatus="count">
                                            <tbody>
                                            <!--enum type stepToString-->
                                            <tr style="text-align: center">
                                                <td>${count.count}</td>
                                                <td><c:out value="${data.studentId}"/></td>
                                                <td><a href="/api/userStatus/approvalUser/${data.studentId}"><c:out value="${data.studentName}"/></a></td>
                                                <td><c:out value="${data.professorName}"/></td>
                                                <td><c:out value="${data.graduationDate}"/></td>
                                                <td><c:out value="${data.step}"/></td>
                                                <td><c:out value="${data.state}"/></td>
                                                <td><c:out value="${data.otherQualifications}"/></td>
                                                <td><c:out value="${data.capstoneCompletion}"/></td>
                                            </tr>
                                            </tbody>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!--페이징 영역 시작-->
                        <div class="text-xs-center">
                            <ul class="pagination justify-content-center">
                                <!-- 이전 -->
                                <c:choose>
                                    <c:when test="${graduator.first}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/api/graduation/graduate_management?page=0&size=10">처음</a>
                                        </li>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/api/graduation/graduate_management?page=${graduator.number-1}&size=10">이전</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <!-- 페이지 그룹 -->
                                <c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
                                    <c:choose>
                                        <c:when test="${graduator.pageable.pageNumber+1 == i}">
                                            <li class="page-item disabled"><a class="page-link"
                                                                              href="/api/graduation/graduate_management?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="/api/graduation/graduate_management?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <!-- 다음 -->
                                <c:choose>
                                    <c:when test="${graduator.last}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/api/graduation/graduate_management?page=${graduator.number+1}&size=10">다음</a>
                                        </li>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/api/graduation/graduate_management?page=${graduator.totalPages-1}&size=10">마지막</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                        <!-- 페이징 영역 끝 -->
                    </div>
                    <form action="graduate_management.download" method="get" onsubmit="return confirm('다운로드 하시겠습니까?')">
                        <button class="btn btn-primary btn-sm float-right">다운로드</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
</body>
</html>