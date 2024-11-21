<template>
  <div>
    <h4>Login</h4>
    <input type="text" placeholder="ID입력" v-model.trim="id" />
    <input type="password" placeholder="PW입력" v-model.trim="password" />
    <button @click="login">로그인</button>
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

</style>
