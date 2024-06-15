function start() {
  const button = document.getElementById("signup");
  button.onclick = check;
}

function check() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const username = document.getElementById("username").value

  if (email === "" || password === "" || username === "") {
    alert("Fields can't be empty!");
    return;
  }
}

window.onload = start;