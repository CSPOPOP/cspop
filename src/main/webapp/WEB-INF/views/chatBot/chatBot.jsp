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
<%@include file="../common/sessionController.jsp" %>
<body>
<%@include file="../common/header.jsp" %>
<section class="page-start">
  <!-- pageheader section -->
  <div class="bg-shape bg-secondary">
    <div class="container">
      <div class="row">
        <div class="offset-xl-1 col-xl-10 col-lg-12 col-md-12 col-12">
          <div class="pt-lg-18 pb-lg-16 py-12 ">
            <div class="row align-items-center">
              <div class="col-lg-12 col-md-12 col-12">
                <div class="custom-breadcrumb">
                  <ol class="breadcrumb">
                    <li class="breadcrumb-item text-white">
                      <a href="/api/home">Home</a>
                    </li>
                    <li class="breadcrumb-item active " aria-current="page">
                      졸업 도우미
                    </li>
                  </ol>
                </div>
              </div>
              <div class="col-lg-6 col-md-6 col-12">
                <h1 class="h2 text-white mb-2">졸업 도우미</h1>
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
                <button onclick="testFunction(`전문교양`)">전문교양</button>
                <button onclick="testFunction(`msc/bsm`)">msc/bsm</button>
                <button onclick="testFunction(`설계학점`)">설계학점</button>
                <button onclick="testFunction(`전공학점`)">전공학점</button>
                <button onclick="testFunction(`총학점`)">총학점</button>
                <button onclick="testFunction(`특이사항`)">특이사항</button>
                <button onclick="testFunction(`전체공학인증`)">전체공학인증</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<%@include file="../common/commonJS.jsp" %>
<script src="../../../../assets/js/detailPage.js">

</script>

<script>
  function testFunction(mode){
    let jsonData = {
      "sentence" : mode
    }
    $.ajax({
      type: 'post',
      url: "/api/chatBot",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(jsonData) ,
      success: (data) => {
        console.log(data.answer)
      },
      error:(error)=>alert("Fail")
    })
  }
</script>
</body>
</html>
