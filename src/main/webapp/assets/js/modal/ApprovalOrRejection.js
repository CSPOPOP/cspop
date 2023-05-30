// 신청접수 외의 승인버튼 --버튼 쪼개기
function ApproveProposalButton(id, studentId,loginId) {
    if(!confirm('승인하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: "/api/userStatus/approvalUserProposalForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("승인처리 완료")
            window.location.reload();
        },
        error:() => alert("승인처리가 되지 않았습니다.")
    })
}

function ApproveInterimButton(id, studentId,loginId) {
    if(!confirm('승인하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: "/api/userStatus/approvalUserInterimForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("승인처리 완료")
            window.location.reload();
        },
        error:() => alert("승인처리가 되지 않았습니다.")
    })
}

function ApproveFinalButton(id, studentId,loginId) {
    if(!confirm('승인하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: "/api/userStatus/approvalUserFinalForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("승인처리 완료")
            window.location.reload();
        },
        error:() => alert("승인처리가 되지 않았습니다.")
    })
}

function ApproveOtherButton(id, studentId,loginId) {
    if(!confirm('승인하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: "/api/userStatus/approvalUserOtherForm/"+studentId,
        type: "post",
        success: ()=>{
            alert("승인처리 완료")
            window.location.reload();
        },
        error:() => alert("승인처리가 되지 않았습니다.")
    })
}

// 신청접수 승인 버튼
function ApplicationReceived(studentId){
    if(!confirm('승인하시겠습니까?')) {
        return;
    }
    const formData = $("#submitFormApproveModal")[0];
    const data = new FormData(formData)
    $.ajax({
        url:'/api/userStatus/approvalUserSubmitForm/'+studentId,
        data: data,
        type: 'post',
        contentType: false,
        processData: false,
        success: () => {
            alert("success")
            window.location.reload();
        },
        error:() => alert("fail")
    })
}
