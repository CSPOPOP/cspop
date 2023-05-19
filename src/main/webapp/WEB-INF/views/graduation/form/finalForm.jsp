<%--
  Created by IntelliJ IDEA.
  User: hamhyeonjun
  Date: 2023/02/22
  Time: 1:09
  To change this template use File | Settings | File Templates.
--%>
<%--
    자주 쓰이는 레이아웃 jsp 파일입니다.

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

        #pages {
            width: 100px;
        }
    </style>
</head>
<%@include file="../../common/sessionController.jsp"%>
<body>
<c:if test="${errorMessage eq true}">
    <script>
        alert("비어있는 항목이 있어 제출할 수 없습니다.")
    </script>
</c:if>
<c:if test="${errorMessage2 eq true}">
    <script>
        alert("파일 용량 제한을 초과하였습니다.");
    </script>
</c:if>
<%@include file="../../common/header.jsp"%>
<section class="page-start">
    <!-- pageheader section -->
    <div class="bg-shape bg-secondary">
        <div class="container">
            <div class="row">
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
                                                    최종보고서
                                                </li>
                                            </ol>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-12">
                                        <h1 class="h2 text-white mb-2"> 최종보고서 작성</h1>
                                    </div>
                                </div>
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
                <div class="col-xl-8 col-lg-8 col-md-7 col-sm-12 col-12 mb-8">
                    <div class="row">
                        <form method="POST" enctype="multipart/form-data" onsubmit="return confirm('제출 하시겠습니까?')">
                            <div class="card" style="padding: 10px;">
                                <input type="hidden" name="studentId" value=${userDetail.studentId}>
                                <input type="hidden" name="studentName" value=${userDetail.studentName}>
                                <input type="hidden" name="department" value=${userDetail.department}>
                                <input type="hidden" name="graduationDate" value=${userDetail.graduationDate}>
                                <input type="hidden" name="advisor" value="${userDetail.advisor}">
                                <!-- <input type="hidden" name="qualification" value="${userDetail.qualification}"> -->
                                <div class="form-group">
                                    <label for="title" class="custom-label">제목</label>
                                    <div class="card" style="padding: 5px;">
                                        <input type="text" class="form-control" id="title" name="title" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>구분</label>
                                    <div class="card" style="padding: 5px;">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" id="category1" name="division"
                                                value="option1" required>
                                            <label class="form-check-label" for="category1">구현논문</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" id="category2" name="division"
                                                value="option2">
                                            <label class="form-check-label" for="category2">조사(이론)논문</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>자격요건</label>
                                    <div class="card" style="padding: 5px;">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="qualification"
                                                id="category3" value="option1" required>
                                            <label class="form-check-label" for="category3">논문양식파일사용</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="qualification"
                                            id="category4" value="option2" required>
                                            <label class="form-check-label" for="category4">목차,서론,본론,결론,참고문헌
                                                포함</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="qualification"
                                            id="category5" value="option3" required>
                                            <label class="form-check-label" for="category5">본인이 직접 작성한 파일임을
                                                확인함</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="pageNumber">쪽수</label>
                                    <div class="card" style="padding: 5px;">
                                        <div class="row">
                                            <div class="col-lg-2 col-3">
                                                <input type="number" min="1" class="form-control" name="pageNumber"
                                                    id="pageNumber" required>
                                            </div>
                                            <div class="col align-self-center">
                                                쪽 (서론/본론/결론/참고문헌 부분 쪽수, 표지/목차 제외)
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label><b>파일 제출</b>(파일 크기는 10MB 이하여야 합니다.)</label>
                                    <div class="card" style="padding: 5px;">
                                        <input id="inputFile" type="file" name="finalFormUploadFile"required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" style="text-align: right; margin-top: 5px;">
                                <label for="sign">서명</label>
                                <input type="text" class="form-control" name="sign" id="sign" placeholder="본인의 이름을 입력"
                                    style="display: inline-block; width: 200px;">
                                <br>
                                <button type="submit" class="btn btn-lg btn-primary"
                                    style="width: 13%; height: 10%;">제출</button>
                                <button type="button" class="btn btn-lg btn-secondary"
                                    style="width: 13%; height: 10%;">취소</button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- sidebar -->
                <div class="col-xl-4 col-lg-4 col-md-5 col-sm-12 col-12 mb-8">
                    <div class="card">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">학번</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark fw-bold">${userDetail.studentId}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">이름</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark fw-bold">${userDetail.studentName}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-user-tie me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">소속학과</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark">${userDetail.department}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">졸업시기</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark fw-bold">${userDetail.graduationDate}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">지도교수</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark fw-bold">${userDetail.advisor}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">자격</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <h5 class="mb-0 text-dark fw-bold">${userDetail.qualification}</h5>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item py-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-file-alt me-2 font-16 fa-fw text-muted"></i>
                                            <h5 class="mb-0 font-weight-medium">캡스톤이수 여부</h5>
                                        </div>
                                    </div>
                                    <div>
                                        <p class="mb-0 text-muted">${userDetail.capstoneCompletionStatus}</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
</body>
</html>
