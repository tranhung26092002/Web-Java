async function getList() {
  try {
    const accessToken = localStorage.getItem("access_token");
    const payloadDecoded = jwt_decode(accessToken);
    const user_id = payloadDecoded.user_id;

    const response = await axios.get(`products/${user_id}`);
    showListProduct(response);
  } catch (error) {
    //error
    if (error.response && error.response.status === 401) {
      window.location.href = "/login.html";
    }
  }
}
getList();
let totalAmount = 0;

function showListProduct(response) {

  let htmlProduct = `<table>
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Xóa</th>
                            </tr>
                        </thead>
                        <tbody id="payment-items">`;
  response.data.forEach(function (product) {
    htmlProduct += `<tr>
                        <td>${product.productName}</td>
                        <td>${product.price} đồng</td>
                        <td>
                            <div class="quantity-container">
                            <button class="quantity"  onclick="decrementQuantity('${product.id}')">-</button>
                            <input class="quantity_input" id="${product.id}" type="number" role="spinbutton" aria-valuenow="1" value="${product.quantity}">
                            <button class="quantity"  onclick="incrementQuantity('${product.id}')">+</button>
                            </div> 
                        </td>
                        <td>
                            <button 
                                id="${product.id}"
                                class="delete"
                                onclick="handleDeleteProduct(this.id)">X
                            </button>
                        </td>
                    </tr>`;

    const productPrice = parseFloat(product.price);
    const productQuantity = parseInt(product.quantity);

    const productTotal = productPrice * productQuantity;
    totalAmount += productTotal;
  });

  htmlProduct += `   </tbody>
                </table>`;

  document.querySelector(".list_container").innerHTML = htmlProduct;
  document.getElementById("total-amount").textContent = `${totalAmount} đồng`;
}

async function handleDeleteProduct(productId) {
  try {
    // call api
    const response = await axios.delete(
      `products/delete/${productId}`
    );
    if (response.status === 200) {
      alert("Xóa thành công!");
      window.location.reload();
    }
  } catch (error) {
    if (error.response.status === 401) {
      window.location.href = "/login.html";
    }
  }
}

async function decrementQuantity(productId) {
  try {
    // lay thong tin
    const quantityInput = document.getElementById(productId);
    const quantity = parseInt(quantityInput.value);

    // Giảm giá trị số lượng
    if (quantity > 1) {
      quantityInput.value = quantity - 1;
    }

    const response = await axios.put(`products/update/${productId}`, {
      quantity: quantityInput.value
    });
    if (response.status === 200) {
      window.location.reload();
    }
  } catch (error) {
    console.log(error);
  }
}
async function incrementQuantity(productId) {
  try {
    // Lấy thông tin số lượng
    const quantityInput = document.getElementById(productId);
    const quantity = parseInt(quantityInput.value) + 1;
    // quantityInput.value = quantity;

    const response = await axios.put(`products/update/${productId}`, {
      quantity: quantity
    });
    if (response.status === 200) {
      window.location.reload();
    }
  } catch (error) {
    console.error(error);
  }
}

async function submitBill() {
  try {
    const accessToken = localStorage.getItem("access_token");
    const payloadDecoded = jwt_decode(accessToken);
    const user_id = payloadDecoded.user_id;

   // lay infor
    const phoneNumber = document.getElementById("phoneNumber").value;
    const address = document.getElementById("address").value;
    let discountCode = document.getElementById("discountCode").value;
    const selectedPaymentMethod = document.querySelector('input[name="method-payment"]:checked').value;
    const selectedDeliveryMethod = document.querySelector('input[name="method-delivery"]:checked').value;
    
    if(totalAmount == 0){
      alert("Vui lòng chọn sản phẩm cần thanh toán.");
      invoiceModal.style.display = "none";
      return;
    }

    // Kiểm tra xem các mục đã được điền đầy đủ chưa
    if (phoneNumber === '' || address === '') {
      if (phoneNumber === '') {
        alert("Vui lòng nhập số điện thoại.");
        invoiceModal.style.display = "none";
        return;
      }else {
        alert("Vui lòng nhập địa chỉ.");
        invoiceModal.style.display = "none";
        return;
      }
    }

    if (discountCode === '') {
      discountCode = "Không sử dụng";
    }

    // gui value
    const response = await axios.post(`bills/add/${user_id}`, {
      totalAmount: totalAmount,
      phoneNumber: phoneNumber,
      address: address,
      discountCode: discountCode,
      deliveryMethod: selectedDeliveryMethod,
      paymentMethod: selectedPaymentMethod,
    });
    if (response.status == 200) {
      console.log("success");
      window.location.href = "bill.html";
    }

  } catch (error) {
    if (error.response.status === 401) {
      window.location.href = "/login.html";
    }
  }
}

async function checkout() {
  try {
    const accessToken = localStorage.getItem("access_token");
    const payloadDecoded = jwt_decode(accessToken);
    const username = payloadDecoded.username;
    const user_id = payloadDecoded.user_id;

    // lay infor
    const phoneNumber = document.getElementById("phoneNumber").value;
    const address = document.getElementById("address").value;
    let discountCode = document.getElementById("discountCode").value;
    const selectedPaymentMethod = document.querySelector('input[name="method-payment"]:checked').value;
    const selectedDeliveryMethod = document.querySelector('input[name="method-delivery"]:checked').value;

    if (discountCode === '') {
      discountCode = "Không sử dụng";
    }


    // Cập nhật thông tin khách hàng
    document.getElementById("customer-name").textContent = username;
    document.getElementById("phone-number").textContent = phoneNumber;
    document.getElementById("address-user").textContent = address;

    // Cập nhật thông tin thanh toán
    document.getElementById("total").textContent = `${totalAmount} VND`;
    document.getElementById("discount-code").textContent = discountCode;
    document.getElementById("payment-method").textContent = selectedPaymentMethod;
    document.getElementById("delivery-method").textContent = selectedDeliveryMethod;

     // Cập nhật thời gian tạo hóa đơn
    const createdAtDate = new Date();
    // Tạo chuỗi giờ và phút
    const currentHours = createdAtDate.getHours();
    const currentMinutes = createdAtDate.getMinutes();

    // Định dạng số giờ và số phút thành chuỗi
    const formattedHours = currentHours < 10 ? '0' + currentHours : currentHours;
    const formattedMinutes = currentMinutes < 10 ? '0' + currentMinutes : currentMinutes;

    const createdAtDateString = `${createdAtDate.getDate()}/${createdAtDate.getMonth() + 1}/${createdAtDate.getFullYear()} - ${formattedHours}:${formattedMinutes}`;
    document.getElementById("created-date").textContent = createdAtDateString;

    const response = await axios.get(`products/${user_id}`);
    const products = response.data; // Giả sử 'response' chứa thông tin hóa đơn

    // Cập nhật danh sách sản phẩm
    const productListUl = document.getElementById("product-list-ul");

    productListUl.innerHTML = ""; // Xóa nội dung hiện tại của danh sách sản phẩm
    products.forEach((product) => {
      const li = document.createElement("li");
      li.textContent = `${product.productName} : ${product.price} x ${product.quantity} = ${product.price * product.quantity} VND`;
      productListUl.appendChild(li);
    });
  } catch (error) {
    if (error.response && error.response.status === 400) {
      // Hiển thị cửa sổ thông báo xác nhận
      alert("Lỗi xác thực tài khoản. Thử lại!");
    }
  }
}
