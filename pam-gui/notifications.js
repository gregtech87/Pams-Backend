function messageBox(message) {
    let boxes = document.querySelector('#messageBoxes');

    boxes.innerHTML = `
    <div id="messageDiv" class="message">
        <div class="alert-box warning"><span>Message: </span>${message}</div>
    </div>
    `;

    setTimeout(function () {
        let box = document.querySelector('#messageDiv');
        box.classList.add('message-hide')
        boxes.innerHTML = ``;
    }, 4000);
}

function successBox(message) {
    let boxes = document.querySelector('#messageBoxes');

    boxes.innerHTML = `
    <div id="messageDiv" class="message">
        <div class="alert-box success"><span>Success: </span>${message}</div>
    </div>
    `;

    setTimeout(function () {
        let box = document.querySelector('#messageDiv');
        box.classList.add('message-hide')
        boxes.innerHTML = ``;
    }, 4000);
}

function errorBox(message) {
    let boxes = document.querySelector('#messageBoxes');

    boxes.innerHTML = `
    <div id="messageDiv" class="message">
        <div class="alert-box error"><span>Error: </span>${message}</div>
    </div>
    `;

    setTimeout(function () {
        let box = document.querySelector('#messageDiv');
        box.classList.add('message-hide')
        boxes.innerHTML = ``;
    }, 4000);
}

