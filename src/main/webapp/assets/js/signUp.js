//아이디 중복 확인
var isCheckedId = 0;
//id 입력란 변확 감지
var isChangedId = 0;

// 학과 선택 함수
$(() => {
    let major = $(`#major`);
    let majorText = `<option> 컴퓨터공학부 </option>`;
    majorText += `<option> 인공지능학부 </option>`;
    majorText += `<option> 안전보안전공 </option>`;
    major.append(majorText);
})

// 비밀번호 찾기 질문 목록 추가 함수
$(() => {
    let questionPw = $(`#questionPw`);
    let questions = `<option> 기억에 남는 추억의 장소는? </option>`;
    questions += `<option> 자신의 보물 제1호는? </option>`;
    questions += `<option> 자신이 가장 존경하는 인물은? </option>`;
    questions += `<option> 내가 좋아하는 캐릭터는? </option>`;
    questions += `<option> 자신의 인생 좌우명은? </option>`;
    questionPw.append(questions);
})

//id 입력란 변화 감지 함수
$(() => {
    const idInput = document.getElementById('id');
    idInput.addEventListener('input', () => {
        isChangedId = 1
    });
})

// 아이디 유효성 검사 함수
function validateId() {
    const idRegExp = RegExp(/^[0-9]{9}$/g);
    //아이디를 입력하지 않은 경우
    if ($(`#id`).val() == '') {
        alert('아이디를 입력해주세요.');
        return false;
    }
    //아이디는 숫자 9자리만 받기
    if (!idRegExp.test($(`#id`).val())) {
        alert('아이디가 형식에 맞지 않습니다.');
        return false;
    }

    return true;
}

// 비밀번호 유효성 검사 함수
function validatePw() {
    const pwRegExp = RegExp(/^(?=.*?[a-zA-Z])(?=.*?[#?!@$ %^&*-]).{8,40}$/);
    if ($(`#password`).val() === '') {
        alert('비밀번호를 입력하세요');
        return false;
    }
    if (!pwRegExp.test($(`#password`).val())) {
        alert('비밀번호가 형식에 맞지 않습니다.');
        return false;
    }
    if ($(`#confirmPassword`).val() === '') {
        alert('비밀번호 확인을 입력하세요');
        return false;
    }
    if ($(`#password`).val() !== ($(`#confirmPassword`).val())) {
        alert('비밀번호와 비밀번호 확인이 같지 않습니다.');
        return false;
    }

    return true;
}

// 아이디, 비밀번호를 제외한 요소들의 유효성 검사 함수
function validateUserInfo() {
    if ($(`#name`).val() == '') {
        alert('이름을 입력하세요');
        return false;
    }
    //radio 선택 여부 확인
    if ($('input[name="gender"]:checked').length < 1) {
        alert('성별을 선택해주세요.');
        return false;
    }
    if ($(`#email`).val() == '') {
        alert('이메일을 입력해주세요.');
        return false;
    }
    if ($(`#phone`).val() == '') {
        alert('전화번호를 입력해주세요.');
        return false;
    }
    //select option 선택 여부 확인
    if ($('[name="major"] > option:selected').val() == '0') {
        alert("전공을 선택해주세요.");
        return false;
    }
    // 비밀번호 찾기 질문 답변 입력 여부 확인
    if ($('#answerPw').val() === '') {
        alert('비밀번호 찾기 질문의 답변을 입력해주세요.');
        return false;
    }

    return true;
}

// 회원가입 함수
function signUp(event) {
    event.preventDefault();
    //아이디 중복확인
    if (isCheckedId === 0 || isChangedId === 1) {
        alert("아이디 중복 확인을 해주세요");
        return false;
    }
    // 아이디 유효성 검사
    if (validateId() === false) {
        return;
    }
    // 비밀번호 유효성 검사
    if (validatePw() === false) {
        return;
    }
    // 성별, 이메일, 전화번호, 전공, 비밀번호 찾기 질문 답변 유효성 검사
    if (validateUserInfo() === false) {
        return;
    }

    if (!confirm('회원가입 하시겠습니까?')) {
        return;
    }

    let userData = {
        studentId: $(`#id`).val(),
        studentPassword: $(`#password`).val(),
        confirmPassword: $(`#confirmPassword`).val(),//비밀번호 확인
        studentName: $(`#name`).val(),
        sex: $('input[name="gender"]:checked').val() + '자',
        birth: "2023-09-01",
        email: $(`#email`).val(),
        phoneNumber: $(`#phone`).val(),
        classification: "학부생",
        department: $(`#major`).val(),
        questionPw: $(`#questionPw`).val(),
        answerPw: $(`#answerPw`).val()
    }

    $.ajax({
        type: "post",
        url: "/api/user", //url작성 부탁드릴게요!
        data: JSON.stringify(userData),
        contentType: "application/json; charset=utf-8",
        success: function (url) {
            alert("회원가입 완료")
            window.location.replace(url);
        },error: (error) => alert(error.responseJSON.errorMessage)
    })
}

//아이디 중복 확인 함수 ---> 따로 통신해야함
function checkId() {
    // 아이디 유효성 검사
    if (validateId() === false) {
        return;
    }
    // api/user/duplicate-check
    const studentId = $(`#id`).val();
    let userData = {
        studentId
    }
    $.ajax({
        type: "post",
        url: "/api/user/duplicate-check", //중복확인url
        data: JSON.stringify(userData),
        contentType: "application/json; charset=utf-8",
        dataTypes: "json",
        success: function () {
            isCheckedId = 1; //중복확인 완료됨.
            isChangedId = 0;
            alert("사용 가능한 아이디 입니다");
        }, error: function (data) {
            isCheckedId = 0; //중복확인이 되지 않음.
            let inputId = document.getElementById('id')
            inputId.value = null;//id를 null 값으로 변경해줌
            alert(data.responseJSON.errorMessage);
        }
    })
}
