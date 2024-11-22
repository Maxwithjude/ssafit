import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/api-user`;

export const useUserStore = defineStore('user', () => {
  const loginUser = ref(null);

  // 로그인 함수
  const userLogin = async (id, password) => {
    try {
      const res = await axios.post(`${REST_API_URL}/login`, { userId : id, userPassword : password });
  
      sessionStorage.setItem('access-token', res.data['access-token']);
  
      const token = res.data['access-token'].split('.');
      const name = JSON.parse(atob(token[1]))['name'];
  
      loginUser.value = name;
  
      router.push({ name: 'boardList' });
    } catch (err) {
      console.error(err);
      throw new Error(err.response?.data?.message || 'ID/PW 정보가 맞지 않습니다.'); // 실패 시 명확한 에러 메시지
    }
  };

  // 로그아웃 함수
  const logout = () => {
    loginUser.value = null;
    sessionStorage.removeItem('access-token');
    alert('정상적으로 로그아웃 처리되었습니다.');
    router.push({ name: 'home' });
  };

// 회원가입 함수
const signup = async ({ userId: id, userPassword: password, userName: name, userNickname: nickname, userEmail: email }) => {
  try {
    const res = await axios.post(`${REST_API_URL}/signup`, { 
      userId: id, 
      userPassword: password, 
      userName: name, 
      userNickname: nickname, 
      userEmail: email 
    });
    alert('회원가입이 성공적으로 완료되었습니다.');
    router.push({ name: 'login' }); // 회원가입 후 로그인 페이지로 이동
  } catch (err) {
    console.error('회원가입 요청 실패:', err);
    alert(err.response?.data?.message || '회원가입에 실패했습니다. 다시 시도해주세요.');
  }
};

  return {
    loginUser,
    userLogin,
    logout,
    signup,
  };
});
