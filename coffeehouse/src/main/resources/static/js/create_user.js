async function handleSubmitAddUser() {
  try {
    // lay thong tin
    const username = document.getElementById("new_username").value;
    const email = document.getElementById("new_email").value;
    const password = document.getElementById("new_password").value;
    const role = document.getElementById("role").value;
    // gui value
    const response = await axios.post("admin/add", {
      username: username,
      email: email,
      password: password,
      role: role,
    });
    if (response.status === 200) {
      alert("Tạo tài khoản thành công.");
      window.location.href = "admin.html";
    }
  } catch (error) {
    console.log("error");
  }
}
