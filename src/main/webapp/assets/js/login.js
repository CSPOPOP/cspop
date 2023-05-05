/*
* login.jsp 사용
* */
function login() {
    let account = {
        loginId: $(`#id`).val(),
        loginPassword: $(`#password`).val()
    }
    $.ajax({
        url: "/api/login/auth",
        type: "post",
        data: JSON.stringify(account),
        contentType: "application/json; charset=utf-8",
        dataTypes: "json",
        success: (url) => {
            alert("로그인 성공")
            window.location.replace(url);
        }, error: (error) => alert(error.responseJSON.errorMessage)

    })
}

// id나 password input에 포커스가 있을 때 enter 누르면 로그인 버튼 클릭 되게 (유효성 검사는 추후에 추가해야합니다)
$(() => {
    $('#id').on("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
          $('#submit-button').click();
        }
      });
      $('#password').on("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
          $('#submit-button').click();
        }
      });
})