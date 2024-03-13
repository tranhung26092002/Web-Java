//show name
const accessToken = localStorage.getItem("access_token");
if(accessToken === null){
  window.location.href = "/login.html";
}else{
  const payloadDecode = jwt_decode(accessToken);
  if(payloadDecode.role != "manager"){
    window.location.href = "/login.html";
    localStorage.removeItem("access_token");
  }
  document.querySelector(".username").innerText = payloadDecode.username;
}


async function getListUser() {
  try {
    // call api get listuser
    const response = await axios.get("manager");
    showListUser(response);
  } catch (error) {
    //error
    if (error.response.status === 401) {
      window.location.href = "/login.html";
    }
  }
}
getListUser();

function showListUser(response) {
  let htmlUser = `<table class="table table-hover text-nowrap">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>`;
  response.data.forEach(function (user, index) {
    htmlUser += `<tr>
                        <td>${index + 1}</td>
                        <td>${user.username}</td>
                        <td>${user.role}</td>
                        <td>${user.email}</td>
                        <td>
                            <button
                            id= "${user.userId}"
                            type="button" class="btn btn-primary"
                            onclick="handleOrder('${user.userId}','${user.username}')">Order
                            </button>
                        </td>
                    </tr>`;
  });
  htmlUser += `   </tbody>
                </table>`;
  document.querySelector(".list_user").innerHTML = htmlUser;
}

function handleOrder(userId, username) {
  // Sử dụng window.location.href để chuyển hướng và truyền tham số qua URL
  window.location.href = `/order.html?userId=${userId}&username=${username}`;
}

function handleLogoutUser() {
  localStorage.removeItem("access_token");
  window.location.href = "/login.html";
}
