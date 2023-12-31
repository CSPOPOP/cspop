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
    // const chatContainer = $('#message-area')
    const chatContainer = document.getElementById('message-area');
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function chatBotAjax(mode) {
    messageArea.append(
        '<div class="alert" role="alert">'+
        '    <p class="card-text">' + mode + '</p>\n' +
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
            messageArea.append(
                '<div class="alert alert-secondary" role="alert">'+
                '    <p class="card-text">' + data.answer + '</p>\n' +
                '</div>'
                )
            scrollToBottom()
        },
        error: () => alert("유효하지 않은 접근 입니다")
    })
}