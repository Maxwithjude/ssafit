<template>
  <div>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink :to="{ name: 'boardList' }">BoardList</RouterLink> |
      <RouterLink :to="{ name: 'boardCreate' }">BoardCreate</RouterLink> |
      <RouterLink :to="{ name: 'youtube' }">러닝영상 검색</RouterLink> |
      <RouterLink :to="{ name: 'youtubeLikes'}">찜한 영상보기</RouterLink> |
      <div class="header-buttons">
        <button v-if="isLoggedIn" @click="logout">로그아웃</button>
        <RouterLink v-else :to="{ name: 'login' }">로그인</RouterLink>
        <RouterLink v-if="!isLoggedIn" :to="{ name: 'Signup'}">회원가입</RouterLink>
      </div>
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

.header-buttons {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-right: 50px;
  }
</style>
