<template>
  <nav class="nav-container" :class="{ 'nav-scrolled': isScrolled }">
    <div class="nav-content">
      <div class="nav-left">
        <RouterLink to="/" class="logo"><img src="@/assets/PaceLink.png" class="PaceLinkIcon" alt="PaceLink">PaceLink</RouterLink>
        <div class="nav-links">
          <RouterLink to="/" class="nav-link">Home</RouterLink>
          <RouterLink :to="{ name: 'boardList' }" class="nav-link">러닝크루 모집</RouterLink>
          <RouterLink :to="{ name: 'youtube' }" class="nav-link">러닝 인기 영상</RouterLink>
          <RouterLink :to="{ name: 'youtubeLikes' }" class="nav-link">찜한 영상</RouterLink>
        </div>
      </div>
      <div class="header-buttons">
        <template v-if="!isLoggedIn">
          <RouterLink :to="{ name: 'login' }" class="login-btn">Login</RouterLink>
          <RouterLink :to="{ name: 'Signup' }" class="signup-btn">Sign Up</RouterLink>
        </template>
        <button v-else @click="handleLogout" class="login-btn">Logout</button>
      </div>
    </div>
  </nav>
 </template>
 
 <script setup>
 import { ref, onMounted, onUnmounted, computed } from 'vue'
 import { useUserStore } from '@/stores/user'
 import { useRouter } from 'vue-router'
 
 // Store와 Router 설정
 const userStore = useUserStore()
 const router = useRouter()
 
 // 반응형 상태
 const isScrolled = ref(false)
 
 // Computed 속성
 const isLoggedIn = computed(() => userStore.loginUser !== null)
 
 // 메서드
 const handleScroll = () => {
  isScrolled.value = window.scrollY > 50
 }
 
 const handleLogout = () => {
  userStore.logout()
  router.push('/')
 }
 
 // 라이프사이클 훅
 onMounted(() => {
  window.addEventListener('scroll', handleScroll)
 })
 
 onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
 })
 </script>
 
 <style scoped>
 .PaceLinkIcon {
  height: 24px;
  margin-right: 8px;
  }

 .nav-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(8px);
  transition: all 0.3s ease;
 }
 
 .nav-scrolled {
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
 }
 
 .nav-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
 }
 
 .nav-left {
  display: flex;
  align-items: center;
  gap: 3rem;
 }
 
 .logo {
  font-size: 1.5rem;
  font-weight: 900;
  color: #f97316;
  text-decoration: none;
 }
 
 .nav-links {
  display: flex;
  gap: 2rem;
 }
 
 .nav-link {
  font-weight: bold;
  text-decoration: none;
  color: #1f2937;
  transition: color 0.3s ease;
  padding: 0.5rem 1rem;
 }
 
 .nav-link:hover,
 .nav-link.router-link-active {
  color: #f97316;
 }
 
 .header-buttons {
  display: flex;
  gap: 1rem;
  align-items: center;
 }
 
 .login-btn, .signup-btn {
  padding: 0.5rem 1.5rem;
  border-radius: 9999px;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.3s ease;
  cursor: pointer;
  border: none;
 }
 
 .login-btn {
  color: #4b5563;
  background: none;
 }
 
 .signup-btn {
  background-color: #f97316;
  color: white;
 }
 
 .login-btn:hover {
  color: #f97316;
 }
 
 .signup-btn:hover {
  background-color: #ea580c;
 }
 
 @media (max-width: 768px) {
  .nav-content {
    padding: 1rem;
  }
  
  .nav-links {
    display: none;
  }
 
  .nav-left {
    gap: 1rem;
  }
  
  .header-buttons {
    gap: 0.5rem;
  }
  
  .login-btn, .signup-btn {
    padding: 0.5rem 1rem;
    font-size: 0.875rem;
  }
 }
 </style>