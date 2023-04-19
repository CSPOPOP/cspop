function ApproveButton(id, userId,loginId) {
    console.log(id);
    console.log(userId)
    console.log(loginId)
    $.ajax({
        url: "/api/userStatus/approvalUser/"+userId,
        type: "post",
        success: (data)=>{
            alert("approve success")
        },
        error:(error)=>{
            alert(error.responseJSON.errorMessage)
        }
    })
}