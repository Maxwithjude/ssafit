<template>
  <div class="signup-page d-flex justify-content-center align-items-center vh-100 bg-light">
    <div class="signup-card card p-5 shadow-sm" style="width: 450px;">
      <div class="text-center mb-4">
        <h3 class="mb-1">회원가입</h3>
      </div>
      <p class="text-muted">필수 정보를 입력하고 회원가입을 완료하세요.</p>
      <form @submit.prevent="handleSignup">
        <div class="mb-3">
          <label for="id" class="form-label">아이디:</label>
          <input id="id" v-model="form.id" class="form-control form-control-lg" required />
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">비밀번호:</label>
          <input id="password" type="password" v-model="form.password" class="form-control form-control-lg" required />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label">이름:</label>
          <input id="name" v-model="form.name" class="form-control form-control-lg" required />
        </div>
        <div class="mb-3">
          <label for="nickname" class="form-label">닉네임:</label>
          <input id="nickname" v-model="form.nickname" class="form-control form-control-lg" required />
        </div>
        <div class="mb-3">
          <label for="email" class="form-label">이메일:</label>
          <input id="email" type="email" v-model="form.email" class="form-control form-control-lg" required />
        </div>
        <button type="submit" class="btn btn-primary btn-lg w-100">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user';
import { reactive } from 'vue';

export default {
  setup() {
    const userStore = useUserStore();
    const form = reactive({
      id: '',
      password: '',
      name: '',
      nickname: '',
      email: '',
    });

    const handleSignup = async () => {
      try {
        await userStore.signup(form);
      } catch (error) {
        alert('회원가입에 실패했습니다. 다시 시도해주세요.');
      }
    };

    return { form, handleSignup };
  },
};
</script>

<style scoped>
.btn-primary {
  background-color: #0056B3; /* 새로운 색상 */
  border: none;
}
.btn-primary:hover {
  background-color: #004494; /* 호버 시 더 진한 파란색 */
}
.signup-card h3 {
  font-weight: 600; /* 헤더를 더 두껍게 */
  color: #333; /* 더 진한 색상 */
}
.signup-card p {
  color: #555; /* 본문 설명의 진한 색상 */
}
</style>
