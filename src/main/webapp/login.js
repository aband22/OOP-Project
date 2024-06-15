function start() {
  const button = document.getElementById("login");
  button.onclick = check;
}

function check() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  if (email === "" || password === "") {
    alert("Fields can't be empty!");
    return;
  }
}

window.onload = start;