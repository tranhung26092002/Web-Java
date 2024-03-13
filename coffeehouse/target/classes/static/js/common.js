axios.defaults.baseURL = "http://localhost:8080/";
axios.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem(
  "access_token"
)}`;

axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    const configUrl = config.url;
    if (configUrl.includes("admin")) {
      config.headers["Authorization"] = `Bearer ${localStorage.getItem(
        "access_token"
      )}`;
    }
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);
