function showContent(section) {
    var contents = document.querySelectorAll('.content');
    contents.forEach(function(content) {
        content.classList.remove('active');
    });
    document.getElementById(section).classList.add('active');
    if (section === 'edit') {
        document.getElementById('usernameEdit').value = document.getElementById('displayUsername').innerText;
    }
}
function save() {
    document.getElementById('displayUsername').innerText = document.getElementById('usernameEdit').value;
    showContent('info');
}
function setAttributeAndSubmit(attribute) {
    var attributeValue = attribute;
    document.getElementById(attribute).value = attributeValue;
    document.getElementById("friendStatus").submit();
}