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
        .nav-pills .nav-link{
            background: white;
        }
    </style>
    <script src="../../../../assets/js/modal/submitFormModal.js"></script>
    <script src="../../../../assets/js/modal/proposalFormModal.js"></script>
    <script src="../../../../assets/js/modal/interimFormModal.js"></script>
    <script src="../../../../assets/js/modal/otherFormModal.js"></script>
    <script src="../../../../assets/js/modal/finalFormModal.js"></script>
    <script src="../../../../assets/js/modal/ApprovalOrRejection.js"></script>
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
                                            나의 졸업 현황
                                        </li>
                                    </ol>
                                </div>
                            </div>
                            <div class="col-lg-12 col-md-12 col-12">
                                <h1 class="h2 text-white mb-2">나의 졸업현황</h1>
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
                    <div class="pt-lg-16 pb-lg-16 py-6 ">
                        <div class="container">
                            <div class="row">
                                <div class="col-xl-8 col-lg-8 col-md-7 col-sm-12 col-12 mb-8">
                                    <div>
                                        <ul class="nav-pills-border nav nav-pills nav-justified mb-5 " id="pills-tab"
                                            role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link active fw-bold" id="thesis-tab-id"
                                                   data-bs-toggle="pill" href="#thesis-id" role="tab"
                                                   aria-controls="thesis" aria-selected="true">
                                                    졸업 논문</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link fw-bold" id="Qualifications-id" data-bs-toggle="pill"
                                                   href="#Qualifications-tab" role="tab"
                                                   aria-controls="Qualifications" aria-selected="false">
                                                    기타 자격</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active show" id="thesis-id" role="tabpanel"
                                                 aria-labelledby="thesis-tab-id"> <!-- 졸업논문 tab 코드 -->
                                                <div id="thesis">
                                                    <div class="progress" style="height: 20px;" id="thesisPercent"></div>
                                                    <table class="table mb-0" border-color="black">
                                                        <thead class="table-success">
                                                        <tr style="text-align: center">
                                                            <th>
                                                                <div class="th-inner sortable both">단계</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">시작날짜</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">종료날짜</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">제출</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">비고</div>
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                        <c:forEach items="${userSchedules}" var="userSchedule">
                                                            <tbody>
                                                            <tr class="text-center">
                                                                <td>${userSchedule.step}</td>
                                                                <td>${userSchedule.startDate}</td>
                                                                <td>${userSchedule.endDate}</td>
                                                                <td>
                                                                    <c:if test="${userSchedule.step eq '신청접수'}">
                                                                        <c:choose>
                                                                            <%--admin 관련해서 모든 관리자 권한에 따른 버튼 view 수정 필요--%>
                                                                            <c:when test="${userId == 'admin1'}">
                                                                                <button class="btn btn-success-soft btn-sm float-right" data-bs-toggle="modal" data-bs-target="#submitFormApprove">
                                                                                    확인
                                                                                </button>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <button class="btn btn-primary-soft btn-sm float-right" data-bs-toggle="modal" data-bs-target="#submitFormModify"
                                                                                        onclick="getSubmitForm(${userSubmitFormInfo.id})">
                                                                                        ${userSchedule.submitStatus}
                                                                                </button>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:if>
                                                                    <c:if test="${userSchedule.step eq '제안서'}">
                                                                        <c:if test="${userSchedule.submitStatus eq '완료'}">
                                                                            <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#proposalFormModify"
                                                                                onclick="getProposalForm(${userProposalFormInfo.id})">
                                                                                ${userSchedule.submitStatus}
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${userSchedule.submitStatus eq '미제출'}">
                                                                            <a class="btn btn-primary btn-sm float-right" href="/api/proposalForm">
                                                                                ${userSchedule.submitStatus}
                                                                            </a>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${userSchedule.step eq '중간보고서'}">
                                                                        <c:if test="${userSchedule.submitStatus eq '완료'}">
                                                                            <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#interimFormModify"
                                                                                onclick="getInterimForm(${userInterimFormInfo.id})">
                                                                                ${userSchedule.submitStatus}
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${userSchedule.submitStatus eq '미제출'}">
                                                                            <a class="btn btn-primary btn-sm float-right" href="/api/interimForm">
                                                                                ${userSchedule.submitStatus}
                                                                            </a>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${userSchedule.step eq '최종보고서'}">
                                                                        <c:if test="${userSchedule.submitStatus eq '완료'}">
                                                                            <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#finalFormModify"
                                                                                onclick="getFinalForm(${userFinalFormInfo.id})">
                                                                                ${userSchedule.submitStatus}
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${userSchedule.submitStatus eq '미제출'}">
                                                                            <a class="btn btn-primary btn-sm float-right" href="/api/finalForm">
                                                                                ${userSchedule.submitStatus}
                                                                            </a>
                                                                        </c:if>
                                                                    </c:if>
                                                                </td>
                                                                <td>${userSchedule.approvalStatus}</td>
                                                            </tr>
                                                            </tbody>
                                                        </c:forEach>
                                                        <div><a href="/api/proposalForm">테스트 제안서 신청 폼 이동</a></div>
                                                        <div><a href="/api/interimForm">테스트 중간 보고서 신청 폼 이동</a></div>
                                                        <div><a href="/api/finalForm">테스트 최종 보고서 신청 폼 이동</a></div>
                                                        <div><a href="/api/otherForm">테스트 기타 자격 신청 폼 이동</a></div>
                                                        <div><a href="/api/userStatus/approvalUser/${userDetail.studentId}/update">신청서 승인 테스트</a></div>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="Qualifications-tab" role="tabpanel"
                                                 aria-labelledby="Qualifications-id"> <!-- 기타자격 tab 코드 -->
                                                <div id="otherQualifications">
                                                    <div class="progress" style="height: 20px;" id="otherPercent"></div>
                                                    <table class="table mb-0" border-color="black">
                                                        <thead class="table-success">
                                                        <tr style="text-align: center">
                                                            <th>
                                                                <div class="th-inner sortable both">단계</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">시작날짜</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">종료날짜</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">제출</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">이동</div>
                                                            </th>
                                                            <th>
                                                                <div class="th-inner sortable both">비고</div>
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                        <c:forEach items="${userSchedules}" var="userSchedule">
                                                            <tbody>
                                                            <tr class="text-center">
                                                                <td>${userSchedule.step}</td>
                                                                <td>${userSchedule.startDate}</td>
                                                                <td>${userSchedule.endDate}</td>
                                                                <td>
                                                                    <c:if test="${userSchedule.step eq '신청접수'}">
                                                                        <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#submitFormModify"
                                                                            onclick="getSubmitForm(${userSubmitFormInfo.id})">
                                                                            ${userSchedule.submitStatus}
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${userSchedule.step eq '제안서'}">
                                                                        <c:if test="${userSchedule.submitStatus eq '완료'}">
                                                                            <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#proposalFormModify"
                                                                                onclick="getProposalForm(${userProposalFormInfo.id})">
                                                                                ${userSchedule.submitStatus}
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${userSchedule.submitStatus eq '미제출'}">
                                                                            <a class="btn btn-primary btn-sm float-right" href="/api/proposalForm">
                                                                                ${userSchedule.submitStatus}
                                                                            </a>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${userSchedule.step eq '기타자격'}">
                                                                        <c:if test="${userSchedule.submitStatus eq '완료'}">
                                                                            <button class="btn btn-primary btn-sm float-right" data-bs-toggle="modal" data-bs-target="#otherFormModify"
                                                                                onclick="getOtherForm(${userOtherFormInfo.id})">
                                                                                ${userSchedule.submitStatus}
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${userSchedule.submitStatus eq '미제출'}">
                                                                            <a class="btn btn-primary btn-sm float-right" href="/api/otherForm">
                                                                                ${userSchedule.submitStatus}
                                                                            </a>
                                                                        </c:if>
                                                                    </c:if>
                                                                </td>
                                                                <td><a href="#">이동</a></td>
                                                                <td>${userSchedule.approvalStatus}</td>
                                                            </tr>
                                                            </tbody>
                                                        </c:forEach>
                                                        <div><a href="/api/proposalForm">테스트 제안서 신청 폼 이동</a></div>
                                                        <div><a href="/api/interimForm">테스트 중간 보고서 신청 폼 이동</a></div>
                                                        <div><a href="/api/finalForm">테스트 최종 보고서 신청 폼 이동</a></div>
                                                        <div><a href="/api/otherForm">테스트 기타 자격 신청 폼 이동</a></div>
                                                        <div><a href="/api/userStatus/approvalUser/${userDetail.studentId}/update">신청서 승인 테스트</a></div>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <p>단계별 과정이 모두 통과하면 최종통과여부: ${finalPass}</p>
                                        <p>제출 버튼 막을 미승인 리스트: ${notApprovalList}</p>
                                        <div>
                                            <button class="btn btn-primary btn-sm float-right"
                                                    data-bs-toggle="modal" data-bs-target="#submitFormModify" onclick="getSubmitForm(${userSubmitFormInfo.id})">신청접수 모달</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="submitFormModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form id="submitFormModal">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h3>신청접수</h3>
                                                            </div>
                                                            <div class="modal-body">

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                                <button type="button" class="btn btn-primary" onclick="clickSubmitFormModify(event, ${userSubmitFormInfo.id})">수정</button>
<%--                                                                <button type="button" class="btn btn-primary" onclick="ApproveButton(${userSubmitFormInfo.id}, ${userDetail.studentId}, userId)">승인</button>--%>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <button class="btn btn-primary btn-sm float-right"
                                                    data-bs-toggle="modal" data-bs-target="#submitFormApprove" onclick="getSubmitForm(${userSubmitFormInfo.id})">신청접수 승인 모달</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="submitFormApprove" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form id="submitFormApproveModal">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h3>신청접수 승인</h3>
                                                            </div>
                                                            <div class="modal-body">
                                                                <h4>신청 요건 <span class="badge bg-info">${userSubmitFormInfo.qualification}</span></h4>
                                                                <label for="professorName">담당교수 :</label>
                                                                <select id="professorName" name="professorName">
                                                                    <option value="한상범">한상범</option>
                                                                    <option value="김도훈">김도훈</option>
                                                                    <option value="이은정">이은정</option>
                                                                    <option value="권기현">권기현</option>
                                                                    <option value="누구있더라">누구있더라</option>
                                                                </select><br>
                                                                <label for="graduationDate">졸업날짜 :</label>
                                                                <input type="date" id="graduationDate" name="graduationDate"><br>
                                                                <label>캡스톤 이수 여부:</label><br>
                                                                <label for="capstoneCompletion-yes">이수</label>
                                                                <input type="radio" id="capstoneCompletion-yes" name="capstoneCompletion" value=true>
                                                                <label for="capstoneCompletion-no">미이수</label>
                                                                <input type="radio" id="capstoneCompletion-no" name="capstoneCompletion" value=false>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                                <button type="button" class="btn btn-primary">반려</button>
                                                                <button type="button" class="btn btn-primary" onclick="ApplicationReceived(${userDetail.studentId})">승인</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <button class="btn btn-primary btn-sm float-right"
                                                    data-bs-toggle="modal" data-bs-target="#proposalFormModify" onclick="getProposalForm(${userProposalFormInfo.id})">제안서 모달</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="proposalFormModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form id="proposalFormModal">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h3>제안서</h3>
                                                            </div>
                                                            <div class="modal-body">

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                                <button type="button" class="btn btn-primary" onclick="clickProposalFormModify(event, ${userProposalFormInfo.id})">수정</button>
                                                                <button type="button" class="btn btn-primary" onclick="ApproveButton(${userSubmitFormInfo.id},${userDetail.studentId}, userId)">승인</button>

                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <button class="btn btn-primary btn-sm float-right"
                                                    data-bs-toggle="modal" data-bs-target="#interimFormModify" onclick="getInterimForm(${userInterimFormInfo.id})">중간 보고서 모달</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="interimFormModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form id="interimFormModal">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h3>중간 보고서</h3>
                                                            </div>
                                                            <div class="modal-body">

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                                <button type="button" class="btn btn-primary" onclick="clickInterimFormModify(event, ${userInterimFormInfo.id})">수정</button>
                                                                <button type="button" class="btn btn-primary" onclick="ApproveButton(${userSubmitFormInfo.id},${userDetail.studentId}, userId)">승인</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                        <button class="btn btn-primary btn-sm float-right"
                                        data-bs-toggle="modal" data-bs-target="#otherFormModify" onclick="getOtherForm(${userOtherFormInfo.id})">기타 자격 모달</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="otherFormModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <form id="otherFormModal">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h3>기타 자격</h3>
                                                        </div>
                                                        <div class="modal-body">

                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                            <button type="button" class="btn btn-primary" onclick="clickOtherFormModify(event, ${userOtherFormInfo.id})">수정</button>
                                                            <button type="button" class="btn btn-primary" onclick="ApproveButton(${userSubmitFormInfo.id},${userDetail.studentId},userId)">승인</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                        </div>
                                        <div>
                                            <button class="btn btn-primary btn-sm float-right"
                                                    data-bs-toggle="modal" data-bs-target="#finalFormModify" onclick="getFinalForm(${userFinalFormInfo.id})">최종 보고서 모달</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="finalFormModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form id="finalFormModal">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h3>최종 보고서</h3>
                                                            </div>
                                                            <div class="modal-body">

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                                <button type="button" class="btn btn-primary" onclick="clickFinalFormModify(event, ${userFinalFormInfo.id})">수정</button>
                                                                <button type="button" class="btn btn-primary" onclick="ApproveButton(${userSubmitFormInfo.id},${userDetail.studentId}, userId)">승인</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
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
                                                            <i class="fas fa-clock me-2 font-16 fa-fw text-muted"></i>
                                                            <h5 class="mb-0 font-weight-medium">졸업시기</h5>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <p class="mb-0 text-muted">${userDetail.graduationDate}</p>
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
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../common/commonJS.jsp" %>
<script>
    const thesisPercentage = {

    }
    const otherPercentage = {

    }
    $(() => { // 졸업, 기타 요건중 유저가 신청한 졸업 전형 table만 띄워준다.
        let image;
        if (${userDetail.otherQualifications} || ${userDetail.thesis}) { //졸업, 기타 요건 중 하나가 true일때,
            if (${userDetail.thesis}) { // 논문이 true일때 기타요건에 들어갈 이미지
                image = "논문으로 신청하셨습니다"
                $('#otherQualifications').html(image)
            } else if (${userDetail.otherQualifications}) { // 기타요건이 true일때 논문에 들어갈 이미지
                image = "기타 자격으로 신청하셨습니다."
                $('#thesis').html(image)
            }
        }
    });
    $(() => { // 기타 자격 progress bar 추가 함수
        let percentage = 100; // percent가 동적으로 변경되어야 함
        let appendText = `<div class="progress-bar bg-success" role="progressbar" aria-label="Basic example" style="width: \${percentage}%" aria-valuenow="\${percentage}" aria-valuemin="0" aria-valuemax="100">\${percentage}%</div>`;
        $('#otherPercent').html(appendText)
    });
    $(() => { // 논문 progress bar 추가 함수
        let percentage = 100; // percent가 동적으로 변경되어야 함
        let appendText = `<div class="progress-bar bg-success" role="progressbar" aria-label="Basic example" style="width: \${percentage}%" aria-valuenow="\${percentage}" aria-valuemin="0" aria-valuemax="100">\${percentage}%</div>`;
        $('#thesisPercent').html(appendText)
    });

</script>
</body>
</html>

