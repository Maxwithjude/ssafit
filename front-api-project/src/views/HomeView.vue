<template>
  <div class="landing-container">
    <nav>
      <TheHeaderNav />
    </nav>

    <!-- 히어로 섹션 -->
    <section class="hero-section">
      <div class="hero-content">
        <h1>RUN TOGETHER</h1>
        <p>함께 뛰며 만드는 특별한 순간</p>
        <div class="hero-buttons">
          <RouterLink
            v-if="!isLoggedIn"
            :to="{ name: 'Signup' }"
            class="primary-btn"
          >
            러닝크루 시작하기
            <span class="btn-arrow">→</span>
          </RouterLink>
        </div>
      </div>
      <div class="background-effects">
        <div class="effect-circle effect-1"></div>
        <div class="effect-circle effect-2"></div>
        <div class="effect-circle effect-3"></div>
      </div>
    </section>

    <!-- 통계 섹션 -->
    <section class="stats-section">
      <div class="stats-container">
        <div v-for="stat in stats" :key="stat.label" class="stat-item">
          <div class="stat-value">{{ stat.value }}</div>
          <div class="stat-label">{{ stat.label }}</div>
        </div>
      </div>
    </section>

    <!-- 영상 섹션 -->
    <section class="video-section">
      <h2 class="section-title">인기 러닝 영상</h2>
      <div class="video-container">
        <YoutubeVideoPopular />
        <YoutubeVideoDetail />
      </div>
    </section>

    <!-- 크루 섹션 -->
    <section class="crew-section">
      <h2 class="section-title">진행중인 크루</h2>
      <div class="crew-container">
        <div
          v-for="crew in crews"
          :key="crew.id"
          class="crew-card"
          :class="{ hovered: hoveredCrewId === crew.id }"
          @mouseenter="hoveredCrewId = crew.id"
          @mouseleave="hoveredCrewId = null"
        >
          <div class="crew-image-container">
            <img :src="crew.image" :alt="crew.title" />
            <div class="crew-badge">{{ crew.status }}</div>
          </div>
          <div class="crew-content">
            <h3>{{ crew.title }}</h3>
            <p class="crew-schedule">{{ crew.schedule }}</p>
            <p class="crew-members">{{ crew.members }}명 참여중</p>
            <button class="join-button">
              참여하기 <span class="arrow-icon">→</span>
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- 채팅 아이콘 -->
    <div v-if="!isChatOpen" @click="toggleChatWindow" class="chat-icon">
      <div class="speech-bubble">
        <h4>채팅을 통해 러닝크루를 모집해보세요!</h4>
      </div>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="30"
        height="30"
        fill="currentColor"
        class="bi bi-chat-right-text-fill"
        viewBox="0 0 16 16"
      >
        <path
          d="M16 2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h9.586a1 1 0 0 1 .707.293l2.853 2.853a.5.5 0 0 0 .854-.353zM3.5 3h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1 0-1m0 2.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1 0-1m0 2.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1"
        />
      </svg>
    </div>

    <!-- 채팅 창 -->
    <div v-if="isChatOpen" class="chat-window">
      <div class="chat-header">
        <div class="header-content">
          <div class="header-left">
            <span class="chat-title">SSAFIT CHAT</span>
            <div class="message-count" v-if="messageCount > 0">
              {{ messageCount }}
            </div>
            <button @click="closeChatWindow" class="close-button">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                fill="currentColor"
                viewBox="0 0 16 16"
              >
                <path
                  d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
      <main class="chat-body">
        <Chat
          :config="config"
          :setSbUserInfo="setSbUserInfo"
          :setUnreadMessageCount="setUnreadMessageCount"
        />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { applyPureReactInVue } from 'veaury'
import ChatReactComponent from '../react_app/Chat.jsx'
import YoutubeVideoDetail from '@/components/youtube/YoutubeVideoDetail.vue'
import YoutubeVideoPopular from '@/components/youtube/YoutubeVideoPopular.vue'
import TheHeaderNav from '@/components/common/TheHeaderNav.vue'

const Chat = applyPureReactInVue(ChatReactComponent)
const SENDBIRD_API_KEY = import.meta.env.VITE_SENDBIRD_API_KEY

// Store와 상태 관리
const userStore = useUserStore()
const isLoggedIn = computed(() => userStore.loginUser !== null)
const isChatOpen = ref(false)
const hoveredCrewId = ref(null)
const userRef = ref(null)
const messageCountRef = ref(null)

// 통계 데이터
const stats = ref([
  { value: '2,000+', label: '활동 러너' },
  { value: '150+', label: '진행중인 크루' },
  { value: '30+', label: '러닝 코스' },
])

// 크루 데이터
const crews = ref([
  {
    id: 1,
    title: '새벽을 여는 러너들',
    schedule: '매일 오전 6시, 올림픽공원',
    members: 15,
    status: '모집중',
    image: '/images/crew1.jpg',
  },
  {
    id: 2,
    title: '한강 따라 달리기',
    schedule: '주말 오전 7시, 여의도한강공원',
    members: 23,
    status: '모집중',
    image: '/images/crew2.jpg',
  },
  {
    id: 3,
    title: '퇴근 후 러닝',
    schedule: '평일 저녁 7시, 청계천',
    members: 18,
    status: '모집중',
    image: '/images/crew3.jpg',
  },
])

// 채팅 설정
const token = sessionStorage.getItem('access-token')
let config = {
  APP_ID: null,
  USER_ID: null,
  NICKNAME: null,
}

if (token) {
  const name = JSON.parse(atob(token.split('.')[1]))['name']
  const nickname = JSON.parse(atob(token.split('.')[1]))['nickname']
  config = {
    APP_ID: SENDBIRD_API_KEY,
    USER_ID: name,
    NICKNAME: nickname,
  }
}

// 메서드
const toggleChatWindow = () => {
  isChatOpen.value = true
}

const closeChatWindow = () => {
  isChatOpen.value = false
}

const setSbUserInfo = (user) => {
  userRef.value = user
}

const setUnreadMessageCount = (count) => {
  messageCountRef.value = count
}

const sbUserInfo = computed(() => userRef.value)
const messageCount = computed(() => messageCountRef.value)
</script>

<style scoped>
.hero-section {
  height: 100vh;
  background: linear-gradient(135deg, #f97316, #ec4899);
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.hero-content {
  text-align: center;
  z-index: 1;
  animation: fadeInUp 1s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.hero-content h1 {
  font-size: 6rem;
  font-weight: 900;
  margin-bottom: 1rem;
  letter-spacing: -2px;
}

.hero-content p {
  font-size: 2rem;
  margin-bottom: 2rem;
}

.primary-btn {
  display: inline-flex;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
  color: #f97316;
  border-radius: 9999px;
  text-decoration: none;
  font-weight: bold;
  transition: all 0.3s ease;
}

.primary-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.btn-arrow {
  margin-left: 0.5rem;
  transition: transform 0.3s ease;
}

.primary-btn:hover .btn-arrow {
  transform: translateX(5px);
}

.background-effects {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.effect-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  filter: blur(60px);
}

.effect-1 {
  width: 400px;
  height: 400px;
  top: -100px;
  left: -100px;
  animation: float 20s infinite;
}

.effect-2 {
  width: 300px;
  height: 300px;
  bottom: -50px;
  right: -50px;
  animation: float 15s infinite reverse;
}

.effect-3 {
  width: 200px;
  height: 200px;
  top: 50%;
  left: 50%;
  animation: float 18s infinite;
}

@keyframes float {
  0%,
  100% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(30px, -30px);
  }
}

.stats-section {
  margin-top: -100px;
  padding: 0 20px;
  position: relative;
  z-index: 2;
}

.stats-container {
  max-width: 1200px;
  margin: 0 auto;
  background: white;
  border-radius: 20px;
  padding: 40px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 40px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.stat-value {
  font-size: 2.5rem;
  font-weight: 700;
  color: #f97316;
  margin-bottom: 0.5rem;
}

.stat-label {
  color: #64748b;
}

.video-section {
  padding: 100px 20px;
}

.section-title {
  text-align: center;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 50px;
  color: #1e293b;
}

.video-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
}

.crew-section {
  padding: 100px 20px;
  background: #fff7ed;
}

.crew-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.crew-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.crew-card:hover {
  transform: translateY(-10px);
}

.crew-image-container {
  position: relative;
  height: 200px;
}

.crew-image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.crew-badge {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: #f97316;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 9999px;
  font-size: 0.875rem;
}

.crew-content {
  padding: 1.5rem;
}

.crew-content h3 {
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.crew-schedule,
.crew-members {
  color: #64748b;
  margin-bottom: 0.5rem;
}

.join-button {
  width: 100%;
  padding: 0.75rem;
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  border: none;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: opacity 0.3s ease;
}

.join-button:hover {
  opacity: 0.9;
}

/* 채팅 아이콘 */
.chat-icon {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #f97316, #ec4899);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 50;
  transition: transform 0.3s ease;
}

.chat-icon:hover {
  transform: translateY(-2px);
}

/* 말풍선 */
.speech-bubble {
  position: absolute;
  right: calc(100% + 1rem);
  bottom: 0;
  background: white;
  padding: 1rem;
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: max-content;
  max-width: 300px;
}

.speech-bubble:after {
  content: '';
  position: absolute;
  right: -10px;
  bottom: 20px;
  border-left: 10px solid white;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
}

.speech-bubble h4 {
  color: #1e293b;
  font-size: 0.875rem;
  margin: 0;
}

/* 채팅 창 */
.chat-window {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 600px;
  height: 600px;
  background: white;
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 100;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-header {
  background: linear-gradient(135deg, #f97316, #ec4899);
  padding: 1rem;
  color: white;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.chat-title {
  font-weight: 600;
  font-size: 1.1rem;
}

.message-count {
  background: white;
  color: #f97316;
  padding: 0.25rem 0.5rem;
  border-radius: 1rem;
  font-size: 0.875rem;
  font-weight: 600;
}

.close-button {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  padding: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: opacity 0.3s ease;
  margin-left: auto;
}

.close-button:hover {
  opacity: 0.8;
}

.chat-body {
  flex: 1;
  overflow: hidden;
  background: #f8fafc;
}

.chat-main {
  flex: 1;
  overflow: hidden;
}

@media (max-width: 1024px) {
  .crew-container {
    grid-template-columns: repeat(2, 1fr);
  }

  .stats-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* 반응형 */
@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 4rem;
  }

  .hero-content p {
    font-size: 1.5rem;
  }

  .video-container {
    grid-template-columns: 1fr;
  }

  .crew-container {
    grid-template-columns: 1fr;
  }

  .stats-container {
    grid-template-columns: 1fr;
  }

  .chat-window {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    margin: 0;
    border-radius: 0;
  }
}

@media (max-width: 480px) {
  .hero-content h1 {
    font-size: 3rem;
  }

  .section-title {
    font-size: 2rem;
  }

  .speech-bubble {
    display: none;
  }
}
</style>
