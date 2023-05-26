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
<%@include file="../../common/sessionController.jsp"%>
<body>
<c:if test="${errorMessage eq true}">
  <script>
    alert("신청서 작성 후 열람이 가능합니다.");
  </script>
</c:if>
<%@include file="../../common/header.jsp"%>
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
                      졸업 신청하기
                    </li>
                  </ol>
                </div>
              </div>
              <div class="col-lg-6 col-md-6 col-12">
                <h1 class="h2 text-white mb-2">졸업 신청하기</h1>
                <p class="text-white-50 lead">
                  졸업 신청서
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
        <%--here--%>
        <div class="col-xl-8 col-lg-8 col-md-7 col-sm-12 col-12 mb-8">

          <div class="row">
            <div class="col-md-6">
              <form method="post" action="/api/submitForm">
                <input type="hidden" name="studentId" value=${userDetail.studentId}>
                <input type="hidden" name="studentName" value=${userDetail.studentName}>
                <input type="hidden" name="department" value="${userDetail.department}">
                <input type="hidden" name="qualification" value="기타자격">
                <button type="submit" class="btn btn-lg btn-primary" style="width: 100%">기타자격</button>
              </form>
            </div>
            <div class="col-md-6">
              <form method="post" action="/api/submitForm">
                <input type="hidden" name="studentId" value=${userDetail.studentId}>
                <input type="hidden" name="studentName" value=${userDetail.studentName}>
                <input type="hidden" name="department" value=${userDetail.department}>
                <input type="hidden" name="qualification" value="논문">
                <button type="submit" class="btn btn-lg btn-primary" style="width: 100%">졸업논문</button>
              </form>
            </div>
          </div>
        </div>
        <!-- sidebar -->
        <div class="col-xl-4 col-lg-4 col-md-5 col-sm-12 col-12 mb-8">
          <div class="card">
            <ul class="list-group list-group-flush">
              <li class="list-group-item py-3">
                <div class="d-flex justify-content-between align-items-center">
                  <%--여기서부터--%>
                  <div>
                    <div class="d-flex align-items-center">
                      <i class="fas fa-money-bill-alt me-2 font-16 fa-fw text-muted"></i>
                      <h5 class="mb-0 font-weight-medium">학번</h5>
                    </div>
                  </div>
                  <div>
                    <h5 class="mb-0 text-dark fw-bold">${userDetail.studentId}</h5>
                  </div>
                  <%--여기까지 js 처리 할 에정--%>
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
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
<script>
  document.addEventListener('keydown', function(event) {
    if (event.keyCode === 13) {
      event.preventDefault();
    }
  }, true);
</script>
</body>
</html>
