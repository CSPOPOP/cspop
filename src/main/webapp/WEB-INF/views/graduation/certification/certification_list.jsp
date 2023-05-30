<%--
  Created by IntelliJ IDEA.
  User: Jung kyoungwon
  Date: 2023-03-29
  Time: 오후 10:38
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
    <script src="../../../../assets/js/ckeditor/ckeditor.js"></script>
    <script src="../../../../assets/js/certification.js"></script>
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
                                        <li class="breadcrumb-item active " aria-current="page">
                                            공학인증 조회
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-12">
                                <h1 class="h2 text-white mb-2">공학인증 조회</h1>
                                <p class="text-white-50 lead">
                                    공학인증 현황
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
                            <div>
                                <div class="card table-responsive">
                                    <table id="table" class="table mb-0" border-color="black">
                                        <thead class="table-dark">
                                        <tr style="text-align: center">
                                            <th data-field="no">
                                                <div class="th-inner sortable both">#</div>
                                            </th>
                                            <th data-field="department">
                                                <div class="th-inner sortable both">소속학과</div>
                                            </th>
                                            <th data-field="studentId">
                                                <div class="th-inner sortable both">학번</div>
                                            </th>
                                            <th data-field="studentName">
                                                <div class="th-inner sortable both">이름</div>
                                            </th>
                                            <th data-field="currentSemester">
                                                <div class="th-inner sortable both">현재학기</div>
                                            </th>
                                            <th data-field="professionalEducation">
                                                <div class="th-inner sortable both">전문교양</div>
                                            </th>
                                            <th data-field="mscBsm">
                                                <div class="th-inner sortable both">MSC/BSM</div>
                                            </th>
                                            <th data-field="design">
                                                <div class="th-inner sortable both">설계</div>
                                            </th>
                                            <th data-field="major">
                                                <div class="th-inner sortable both">전공</div>
                                            </th>

                                            <th data-field="essential">
                                                <div class="th-inner sortable both">필수</div>
                                            </th>
                                            <th data-field="firstAndLast">
                                                <div class="th-inner sortable both">선/후수</div>
                                            </th>
                                            <th data-field="total">
                                                <div class="th-inner sortable both">총 학점</div>
                                            </th>
                                            <th data-field="specialNote">
                                                <div class="th-inner sortable both">특이사항</div>
                                            </th>
                                        </tr>
                                        </thead>
                                        <c:forEach var="data" items="${certification.content}" varStatus="count">
                                            <tbody>
                                            <!--enum type stepToString-->
                                            <tr style="text-align: center">
                                                <td>${count.count}</td>
                                                <td><c:out value="${data.department}"/></td>
                                                <td><c:out value="${data.studentId}"/></td>
                                                <td><c:out value="${data.studentName}"/></td>
                                                <td><c:out value="${data.currentSemester}"/></td>
                                                <td><c:out value="${data.professionalEducation}"/></td>
                                                <td><c:out value="${data.mscBsm}"/></td>
                                                <td><c:out value="${data.design}"/></td>
                                                <td><c:out value="${data.major}"/></td>
                                                <td><c:out value="${data.essential}"/></td>
                                                <td><c:out value="${data.firstAndLast}"/></td>
                                                <td><c:out value="${data.total}"/></td>
                                                <td><c:out value="${data.specialNote}"/></td>
                                            </tr>
                                            </tbody>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>

                            <!-- Modal -->
                            <div class="modal fade" id="modifyTable" data-bs-backdrop="static" data-bs-keyboard="false"
                                 tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h3>파일 올리기</h3>
                                        </div>
                                        <form action="certification_management.read" id="certificationForm"
                                              method="post" enctype="multipart/form-data">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <input type="file" name="file"/>

                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    취소
                                                </button>
                                                <button type="submit" class="btn btn-primary"
                                                        onclick="uploadExcel(event)">업로드
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--페이징 영역 시작-->
                        <div class="text-xs-center">
                            <ul class="pagination justify-content-center">
                                <!-- 이전 -->
                                <c:choose>
                                    <c:when test="${certification.first}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/api/graduation/certification_management?page=0&size=10">처음</a>
                                        </li>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/api/graduation/certification_management?page=${certification.number-1}&size=10">이전</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <!-- 페이지 그룹 -->
                                <c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
                                    <c:choose>
                                        <c:when test="${certification.pageable.pageNumber+1 == i}">
                                            <li class="page-item disabled"><a class="page-link"
                                                                              href="/api/graduation/certification_management?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="/api/graduation/certification_management?page=${i-1}&size=10">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <!-- 다음 -->
                                <c:choose>
                                    <c:when test="${certification.last}"></c:when>
                                    <c:otherwise>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/api/graduation/certification_management?page=${certification.number+1}&size=10">다음</a>
                                        </li>
                                        <li class="page-item "><a class="page-link"
                                                                  href="/api/graduation/certification_management?page=${certification.totalPages-1}&size=10">마지막</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                        <!-- 페이징 영역 끝 -->
                    </div>
                    <form action="certification_management.download" method="get"
                          onclick="return confirm('다운로드 하시겠습니까?')">
                        <button class="btn btn-primary btn-sm float-right">다운로드</button>
                    </form>
                    <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal"
                            data-bs-target="#modifyTable">파일 올리기
                    </button>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
</body>
</html>