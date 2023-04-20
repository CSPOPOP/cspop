// 신청접수 외의 승인버튼
function ApproveButton(id, studentId,loginId) {
    console.log(id);
    console.log(studentId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUser/"+studentId,
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
        url:'/api/userStatus/approvalUser/'+studentId,
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
