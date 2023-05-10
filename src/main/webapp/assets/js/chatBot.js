function testFunction(mode) {
    let jsonData = {
        "sentence": mode
    }
    $.ajax({
        type: 'post',
        url: "/api/chatBot",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(jsonData),
        success: (data) => {
            console.log(data.answer)
        },
        error: (error) => alert("Fail")
    })
}

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
