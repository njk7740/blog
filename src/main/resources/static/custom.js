function autoResize(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}
function applyPreviewContent(textarea) {
    document.querySelector('.preview > p').textContent = textarea.value;
}
function applyPreviewSubject(input) {
    document.querySelector('.preview > h2').textContent = input.value;
}