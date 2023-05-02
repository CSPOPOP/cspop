//비밀번호 1,2 확인 함수
function checkPw(pw1, pw2) {
    return pw1 !== pw2 ? 1 : 0;
}

$(() => {
    let questionPw = $(`#questionPw`);
    let questions = `<option> 기억에 남는 추억의 장소는? </option>`;
    questions += `<option> 자신의 보물 제1호는? </option>`;
    questions += `<option> 자신이 가장 존경하는 인물은? </option>`;
    questions += `<option> 내가 좋아하는 캐릭터는? </option>`;
    questions += `<option> 자신의 인생 좌우명은? </option>`;
    questionPw.append(questions);
})

// 아이디 유효성 검사 함수
function validateId() {
    // 아이디 입력이 비어있는 경우
    if ($(`#id`).val() === '') {
        alert('아이디를 입력해주세요.');
        return false;
    }

    // 아이디는 숫자로만 받기(학번으로)
    const idRegExp = /^[0-9]{9}$/g;//정규식.숫자만 입력.9자리만
    if (false === idRegExp.test($(`#id`).val())) {
        alert('아이디가 형식에 맞지 않습니다.')
        return false;
    }
    return true;
}

// 비밀번호 찾기 질문 유효성 검사 함수
function validateAnswerPw(){
    if ($(`#answerPw`).val() === '') {
        alert('비밀번호 찾기 질문을 입력해주세요.');
        return false;
    }
}
//  비밀번호 유효성 검사 함수
function validatePassword() {
    const pwRegExp = /^(?=.*?[a-zA-Z])(?=.*?[#?!@$ %^&*-]).{8,40}$/; //비밀번호 8자리 이상. 대문자. 특수문자 포함 정규식

    // 비밀번호 입력이 비어있는 경우
    if ($(`#password`).val() === '') {
        alert('비밀번호를 입력해주세요.');
        return false;
    }
    // 비밀번호 형식 검사
    if (false === pwRegExp.test($(`#password`).val())) {
        alert('비밀번호가 형식에 맞지 않습니다.');
        return false;
    }
    // 비밀번호 확인 입력이 비어있는 경우
    if ($(`#password2`).val() === '') {
        alert('비밀번호 확인을 입력해주세요.');
        return false;
    }
    // 비밀번호 확인 검사
    if (checkPw($('#password').val(), $('#password2').val())) {
        alert('비밀번호 확인이 일치하지 않습니다.');
        return false;
    }
    return true;
}

//db로부터 저장된 학번가져오기
function getStudentNumber(target) {
    if (validateId() === false) {
        return;
    }
    // $(`#questionPw`).text("확인 완료");
    // $('#id').attr('disabled', true);
    // $(target).attr("disabled", true);
    let userData = {
        studentId: $(`#id`).val()
    }
    $.ajax({
        url: "/api/passwordQuestion",
        type: "post",
        data: JSON.stringify(userData),
        contentType: "application/json; charset=utf-8",
        dataTypes: "json",
        success: (data) => {
            // 아이디 인풋, 확인 버튼 비활성화.
            $('#id').attr('disabled', true);
            $(target).attr("disabled", true);
            // 요소에 질문 표시
            //$(`#questionPw`).text(data.questionPw);
        },
        error: (error) => alert(error.responseJSON.errorMessage)

    })
}

//학번에 해당하는 비밀번호 답 가져오기
function passwordAnswer(target){

    // 아이디(학번) 유효성 검사
    if (validateId() === false) {
        return;
    }
    // 질문의 답변이 비어있는지 검사
    if ($(`#answerPw`).val() === '') {
        alert('질문의 답변을 입력해주세요.');
        return;
    }

    let answerData = {
        answerPw: $(`#answerPw`).val(),
    }

    $.ajax({
        url: `/api/answerPassword`,
        type: "post",
        data: JSON.stringify(answerData),
        contentType: "application/json; charset=utf-8",
        dataTypes: "json",
        success: (answerData) => {
            $('#answerPw').attr('disabled', true);
            $(target).attr("disabled", true);
        }, error: (error) => alert(error.responseJSON.errorMessage)

    })
}

// 서버에 비밀번호 리셋 요청 함수
function resetPassword() {

    // 아이디(학번) 유효성 검사
    if (validateId() === false) {
        return;
    }

    if (validateAnswerPw()===false){
        return;
    }

    // 비밀번호 유효성 검사
    if (validatePassword() === false) {
        return;
    }


    const studentId = $(`#id`).val();
    let editData = {
        studentPassword: $(`#password`).val()
    }

    $.ajax({
        url: `/api/editPassword/${studentId}`,
        type: "post",
        data: JSON.stringify(editData),
        contentType: "application/json; charset=utf-8",
        dataTypes: "json",
        success: (url) => {
            alert("비밀번호 재설정 성공")
            window.location.replace(url); // 로그인 화면으로 이동
        }, error: (error) => alert(error.responseJSON.errorMessage)

    })
}