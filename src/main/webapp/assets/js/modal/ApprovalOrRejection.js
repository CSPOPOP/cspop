// 신청접수 외의 승인버튼 --버튼 쪼개기
function ApproveProposalButton(id, studentId,loginId) {
    console.log(id);
    console.log(studentId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUserProposalForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("approve success")
        },
        error:(error)=>{
            console.log(error)
        }
    })
}

function ApproveInterimButton(id, studentId,loginId) {
    console.log(id);
    console.log(studentId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUserInterimForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("approve success")
        },
        error:(error)=>{
            console.log(error)
        }
    })
}

function ApproveFinalButton(id, studentId,loginId) {
    console.log(id);
    console.log(studentId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUserFinalForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("approve success")
        },
        error:(error)=>{
            console.log(error)
        }
    })
}

function ApproveOtherButton(id, studentId,loginId) {
    console.log(id);
    console.log(studentId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUserOtherForm/"+studentId,
        type: "post",
        success: (data)=>{
            alert("approve success")
        },
        error:(error)=>{
            console.log(error)
        }
    })
}

// 신청접수 승인 버튼
function ApplicationReceived(studentId){
    const formData = $("#submitFormApproveModal")[0];
    const data = new FormData(formData)
    console.log(formData)
    console.log(data)
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
        error:(data)=>{
            console.log(data)
            alert("fail")
        }
    })
}
