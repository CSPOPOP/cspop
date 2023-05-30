const userInput = $('#user-input');
const messageArea = $('#message-area');
userInput.on('keyup', (event) => {
    if (event.key === 'Enter') {
        const userMessage = userInput.val().trim();

        if (userMessage !== '') {
            messageArea.append('<div class="message user-message">' + userMessage + '</div>');
            scrollToBottom()
            userInput.val('');
        }
    }
});

function scrollToBottom() {
    const chatContainer = $('#message-area');
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function chatBotAjax(mode) {
    messageArea.append(
        '<div class="card float-left container" style="width: 18rem;">\n' +
        '  <div class="card-body">\n' +
        '    <h5 class="card-title">Card title</h5>\n' +
        '    <p class="card-text">' + mode + '</p>\n' +
        '  </div>\n' +
        '</div>'
    );
    let jsonData = {
        "sentence": mode
    }
    $.ajax({
        type: 'post',
        url: "/api/chatBot",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(jsonData),
        success: (data) => {
            // messageArea.append('<div class="message user-message">' + data.answer + '</div>')
            messageArea.append(
                '<div class="card float-right container" style="width: 18rem;">\n' +
                '  <div class="card-body">\n' +
                '    <h5 class="card-title">ChatBot</h5>\n' +
                '    <p class="card-text">' + data.answer + '</p>\n' +
                '  </div>\n' +
                '</div>')
            scrollToBottom()
        },
        error: (error) => alert("유효하지 않은 접근 입니다")
    })
}