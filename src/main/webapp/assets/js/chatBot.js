

const userInput = $('#user-input');
const messageArea = $('#message-area');
userInput.on('keyup', (event) => {
    if (event.key === 'Enter') {
        const userMessage = userInput.val().trim();

        if (userMessage !== '') {
            messageArea.append('<div class="message user-message">' + userMessage + '</div>');
            userInput.val('');
        }
    }
});

function chatBotAjax(mode) {
    messageArea.append('<div class="message user-message">' + mode + ' 알려줘</div>');
    let jsonData = {
        "sentence": mode
    }
    $.ajax({
        type: 'post',
        url: "/api/chatBot",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(jsonData),
        success: (data) => messageArea.append('<div class="message user-message">' + data.answer + '</div>'),
        error: (error) => alert("유효하지 않은 접근 입니다")
    })
}