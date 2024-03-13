async function addToCartProduct(productName, productPrice) {
  try {
    // Lấy ID của người dùng từ token
    const accessToken = localStorage.getItem("access_token");
    const payloadDecoded = jwt_decode(accessToken);
    const user_id = payloadDecoded.user_id;
    console.log(user_id);

    // Tạo một đối tượng chứa thông tin sản phẩm
    const productData = {
      userId: user_id,
      productName: productName,
      price: productPrice,
    };

    const response = await axios.post("products/add", productData);

    if (response.status === 200) {
      alert("Đã thêm vào giỏ hàng!");
    } else {
      console.error("Lỗi:", response.status);
    }
  } catch (error) {
    console.error("Lỗi:", error);
  }
}
