import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const REST_API_URL = `http://localhost:8080/api-user`;

export const useUserStore = defineStore("user", () => {
  const loginUser = ref(null);

  // 로그인 함수
  const userLogin = async (id, password) => {
    try {
      const res = await axios.post(`${REST_API_URL}/login`, { userId: id, userPassword: password });

      sessionStorage.setItem("access-token", res.data["access-token"]);

      const token = res.data["access-token"].split(".");
      const name = JSON.parse(atob(token[1]))["name"];

      loginUser.value = name;

      router.push({ name: "boardList" });
    } catch (err) {
      console.error(err);
      throw new Error(err.response?.data?.message || "ID/PW 정보가 맞지 않습니다.");
    }
  };

  // 로그아웃 함수
  const logout = () => {
    loginUser.value = null;
    sessionStorage.removeItem("access-token");
    alert("정상적으로 로그아웃 처리되었습니다.");
    router.push({ name: "home" });
  };

  // 회원가입 함수
  const signup = async (form) => {
    try {
      await axios.post(`${REST_API_URL}/signup`, {
        userId: form.id,
        userPassword: form.password,
        userName: form.name,
        userNickname: form.nickname,
        userEmail: form.email,
      });
      alert("회원가입이 성공적으로 완료되었습니다.");
      router.push({ name: "login" });
    } catch (err) {
      console.error("회원가입 요청 실패:", err);
      alert(err.response?.data?.message || "회원가입에 실패했습니다. 다시 시도해주세요.");
    }
  };

  // **추가된 함수: 사용자 정보 가져오기**
  const fetchUserInfo = async () => {
    try {
      const response = await axios.get(`${REST_API_URL}/userinfo`, {
        headers: { Authorization: `Bearer ${sessionStorage.getItem("access-token")}` },
      });
      return response.data;
    } catch (error) {
      console.error("사용자 정보 가져오기 실패:", error);
      throw new Error("사용자 정보를 불러오지 못했습니다.");
    }
  };

  return {
    loginUser,
    userLogin,
    logout,
    signup,
    fetchUserInfo, // 추가된 부분
  };
});
