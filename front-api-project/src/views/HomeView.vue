<template>
  <div>
    <h2>HomeView</h2>
    <v-divider :thickness="5" color="info"></v-divider>
    <ChatBot />
    <v-divider :thickness="5" color="info"></v-divider>
    <YoutubeVideoDetail />
    <v-divider :thickness="5" color="info"></v-divider>
    <YoutubeVideoPopular />
    <v-divider :thickness="5" color="info"></v-divider>
    <!-- 로그인 하지 않으면 렌더링 하지 않는다.-->>
    <div class="speech-bubble">
      <br />
      <h4><strong>채팅을 통해 러닝크루를 모집해보세요!</strong></h4>
    </div>
    <!-- Chat Icon -->
    <div v-if="!isChatOpen" @click="toggleChatWindow" class="chat-icon">
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
    <!-- 클릭했을 시 보일 채팅 윈도우-->
    <div v-if="isChatOpen" class="chat-window">
      <div class="chat-header">
        <header class="header">
          <svg
            class="logo"
            viewBox="0 0 128 128"
            width="24"
            height="24"
            data-v-97393f76=""
          >
            <path
              fill="#42b883"
              d="M78.8,10L64,35.4L49.2,10H0l64,110l64-110C128,10,78.8,10,78.8,10z"
              data-v-97393f76=""
            ></path>
            <path
              fill="#35495e"
              d="M78.8,10L64,35.4L49.2,10H25.6L64,76l38.4-66H78.8z"
              data-v-97393f76=""
            ></path>
          </svg>
          SSAFIT
          <div class="message-count">
            <img class="message-icon" src="@/assets/message.png" alt="" />
            <div class="current-count" v-if="messageCount > 0">
              {{ messageCount }}
            </div>
          </div>
          <button @click="closeChatWindow" id="closeBtn">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="25"
              height="25"
              fill="currentColor"
              class="bi bi-x-square-fill"
              viewBox="0 0 16 16"
            >
              <path
                d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708"
              />
            </svg>
          </button>
          <div class="nickname">{{ sbUserInfo?.nickname }}</div>
        </header>

        <main class="message-window">
          <Chat
            :config="config"
            :setSbUserInfo="setSbUserInfo"
            :setUnreadMessageCount="setUnreadMessageCount"
          />
        </main>
      </div>
    </div>
  </div>
</template>
<!-- scrip setup 제거함 주의-->
<script>
import { applyReactInVue, applyPureReactInVue } from 'veaury'
import { ref } from 'vue'

import ChatReactComponent from '../react_app/Chat.jsx'
import ChatBot from '@/components/chatbot/ChatBot.vue'
import YoutubeVideoPopular from '@/components/youtube/YoutubeVideoPopular.vue'
import YoutubeVideoDetail from '@/components/youtube/YoutubeVideoDetail.vue'

const SENDBIRD_API_KEY = import.meta.env.VITE_SENDBIRD_API_KEY

export default {
  components: {
    YoutubeVideoDetail,
    YoutubeVideoPopular,
    Chat: applyPureReactInVue(ChatReactComponent),
  },
  setup() {
    //토글
    const isChatOpen = ref(false)

    // 채팅 창 열기/닫기 토글
    const toggleChatWindow = () => {
      isChatOpen.value = true
    }

    // 채팅 창 닫기
    const closeChatWindow = () => {
      isChatOpen.value = false
    }

    //로그인이 안되어 있으면 알림

    const token = sessionStorage.getItem('access-token')
    // if (!token) {
    //   alert("로그인 시 이용 가능합니다.");
    // } else {
    let config = {
      APP_ID: null,
      USER_ID: null,
      NICKNAME: null,
    }

    if (token) {
      const name = JSON.parse(atob(token.split('.')[1]))['name']
      const nickname = JSON.parse(atob(token.split('.')[1]))['nickname']
      console.log(name)
      console.log(nickname)
      config = {
        APP_ID: SENDBIRD_API_KEY,
        USER_ID: name,
        NICKNAME: nickname,
      }
    } else {
      // alert("채팅 기능은 로그인 시 이용 가능합니다.");
    }
    const userRef = ref(null)
    const messageCountRef = ref(null)
    return {
      isChatOpen,
      toggleChatWindow,
      closeChatWindow,
      config: config,
      setSbUserInfo: (user) => {
        userRef.value = user
      },
      setUnreadMessageCount: (count) => {
        messageCountRef.value = count
      },
      sbUserInfo: userRef,
      messageCount: messageCountRef,
    }
  },
}
</script>

<style scoped>
/* 채팅 아이콘 스타일 */
.chat-icon {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  background-color: #58faf4;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  z-index: 9999;
}

.chat-icon img {
  width: 40px;
  height: 40px;
}

/* 채팅 창 스타일 */
.chat-window {
  position: fixed;
  height: 600px;
  bottom: 10px; /* 화면 아래에 배치 */
  right: 10px; /* 화면 오른쪽에 배치 */
  width: 800px; /* 기본적으로 350px 너비로 설정 (필요에 따라 더 확장 가능) */
  max-width: 90vw; /* 최대 너비는 화면 너비의 90%로 설정 */
  max-height: 80vh; /* 최대 높이는 화면의 80%로 제한 */
  overflow-y: auto; /* 내용이 많으면 스크롤이 생기도록 설정 */
  background-color: #fff;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  border-radius: 8px; /* 모서리 둥글게 */
  display: flex;
  flex-direction: column;
  transition: height 0.3s ease-in-out; /* 애니메이션 효과 */
}

.chat-header {
  display: flex; /* Flexbox 활성화 */
  flex-direction: column; /* 수직 방향 정렬 */
  flex: 5;
}

.message-window {
  flex: 1;
}

.nickname {
  display: inline;
  font-size: 17px;
  float: right;
  margin-right: 8px;
}
.message-count {
  display: inline;
  position: relative;
  font-size: 17px;
  float: right;
}

.message-icon {
  width: 32px;
  filter: invert(1);
}

.current-count {
  position: absolute;
  top: -5px;
  right: -8px;
  font-size: 12px;
  background: #d02929;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  text-align: center;
}

.header {
  display: flex;
  justify-content: flex-start;
  border-bottom: 1px solid #eaeaea;
  font-weight: 700;
  background-color: #742ddd;
  padding: 15px;
  color: white;
  font-size: 18px;
  box-shadow: 0px 0px 2px 0px #ccc;
}

.logo {
  display: inline-block;
  vertical-align: text-bottom;
}

#closeBtn {
  margin-left: auto;
  color: #42b983;
  background-color: #742ddd;
  padding: 0.1%;
}

.speech-bubble {
  font-style: oblique;
  position: fixed;
  bottom: 20px;
  right: 100px;
  width: 250px;
  height: 100px;
  background: #e4d8f4;
  border-radius: 0.4em;

  /* 중앙 정렬을 위한 Flexbox */
  display: flex; /* Flexbox 활성화 */
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  text-align: center; /* 텍스트 줄바꿈 시 가운데 정렬 */
  padding: 10px; /* 텍스트와 말풍선 가장자리 간격 */
}

.speech-bubble:after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 20px solid transparent;
  border-left-color: #e4d8f4;
  border-right: 0;
  border-bottom: 0;
  margin-top: -10px;
  margin-right: -20px;
}
</style>
