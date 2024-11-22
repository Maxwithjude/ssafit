<template>
  <div class="login-page d-flex justify-content-center align-items-center vh-100 bg-light">
  <div class="login-card card p-5 shadow-sm" style="width: 400px;">
    <div class="text-center mb-4">
      <h3 class="mb-1">로그인</h3>
    </div>
    <p class="text-center mb-4">
      아이디와 비밀번호를 입력해 로그인해주세요.<br />
      아직 회원이 아니라면 <a href="#" class="signup-link">회원가입</a>을 먼저 해주시기 바랍니다.
    </p>
    <div class="mb-3">
      <input 
        type="text" 
        class="form-control" 
        placeholder="ID 입력" 
        v-model.trim="id" 
      />
    </div>
    <div class="mb-3">
      <input 
        type="password" 
        class="form-control" 
        placeholder="PW 입력" 
        v-model.trim="password" 
      />
    </div>
    <button 
      class="btn btn-primary w-100" 
      @click="login"
    >
      로그인
    </button>
  </div>
</div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const id = ref('');
const password = ref('');

const login = async function () {
try {
  await store.userLogin(id.value, password.value);
  alert('로그인 성공!');
} catch (error) {
  alert('로그인 실패: ' + error.message); // 실패 시 정확한 실패 메시지 표시
}
};

</script>

<style scoped>
.login-page {
background-color: #f8f9fa; /* 밝은 회색 배경 */
}

.login-card {
border-radius: 8px; /* 카드 모서리 둥글게 */
}

.login-card h3 {
font-weight: 600; /* 헤더를 더 두껍게 */
color: #333; /* 더 진한 색상 */
}
.login-card p {
color: #555; /* 본문 설명의 진한 색상 */
}

.btn-primary {
background-color: #0056b3; /* SSAFY와 유사한 파란색 */
border: none;
}
.btn-primary:hover {
background-color: #004494; /* 호버 시 더 진한 파란색 */
}
.signup-link {
color: #3C90E2; /* 회원가입 텍스트 색상 */
text-decoration: none;
}
.signup-link:hover {
text-decoration: underline; /* 마우스 오버 시 밑줄 표시 */
}
</style>
