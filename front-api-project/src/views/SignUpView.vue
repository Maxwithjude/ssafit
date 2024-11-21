<template>
  <div>
    <h1>회원가입</h1>
    <form @submit.prevent="handleSignup">
      <div>
        <label for="id">아이디:</label>
        <input id="id" v-model="form.id" required />
      </div>
      <div>
        <label for="password">비밀번호:</label>
        <input id="password" type="password" v-model="form.password" required />
      </div>
      <div>
        <label for="name">이름:</label>
        <input id="name" v-model="form.name" required />
      </div>
      <button type="submit">회원가입</button>
    </form>
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
    });

    const handleSignup = async (e) => {
      e.preventDefault(); // 기본 동작 방지 (중복 호출 예방)
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
