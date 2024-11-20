<template>
  <div>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink :to="{ name: 'boardList' }">BoardList</RouterLink> |
      <RouterLink :to="{ name: 'boardCreate' }">BoardCreate</RouterLink> |
      <RouterLink :to="{ name: 'kakao' }">KakaoMap</RouterLink> |
      <RouterLink :to="{ name: 'tmdb' }">TMDB</RouterLink> |
      <RouterLink :to="{ name: 'youtube' }">Youtube</RouterLink> |
      <button v-if="isLoggedIn" @click="logout">로그아웃</button>
      <RouterLink v-else :to="{ name: 'login' }">로그인</RouterLink> |
      <RouterLink v-if="!isLoggedIn" :to="{ name: 'Signup'}">회원가입</RouterLink>
    </nav>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user';
import { computed } from 'vue';

export default {
  setup() {
    const userStore = useUserStore();

    const isLoggedIn = computed(() => userStore.loginUser !== null);

    const logout = () => {
      userStore.logout();
    };

    return {
      isLoggedIn,
      user: userStore.loginUser,
      logout,
    };
  },
};
</script>

<style scoped>
nav {
  text-align: center;
}
nav a {
  font-weight: bold;
  text-decoration: none;
  color: black;
}
nav a.router-link-exact-active {
  color: #42b983;
}
</style>
