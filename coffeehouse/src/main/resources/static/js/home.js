//show name
const accessToken = localStorage.getItem("access_token");
if(accessToken === null){
  window.location.href = "/login.html";
}else{
  const payloadDecode = jwt_decode(accessToken);
  if(payloadDecode.role != "customer"){
    window.location.href = "/login.html";
    localStorage.removeItem("access_token");
  }
  document.querySelector(".name_user").innerText = payloadDecode.username;
}


function handleTrangchu() {
  window.location.href = "home.html";
}

function handleFood() {
  window.location.href = "food.html";
}

function handleDrink() {
  window.location.href = "drink.html";
}

function handlePay() {
  window.location.href = "thanhtoan.html";
}

function handleNews() {
  window.location.href = "tintuc.html";
}

function handleAddress() {
  window.location.href = "diachi.html";
}

function handleBill() {
  window.location.href = "bill.html";
}

function inforUser() {
  window.location.href = "user.html";
}

function handleLogoutUser() {
  localStorage.removeItem("access_token");
  window.location.href = "login.html";
}
