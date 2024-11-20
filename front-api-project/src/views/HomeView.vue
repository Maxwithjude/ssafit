<template>
  <div>
    <h2>HomeView</h2>
    <hr />
    <WeatherForecast />
    <hr />
    <ChatBot />
    <hr />
    <YoutubeVideoDetail />
    <hr />
    <YoutubeVideoPopular />
    <hr />
    <!-- 로그인 하지 않으면 렌더링 하지 않는다.-->>
    <!-- Chat Icon -->
    <div v-if="!isChatOpen" @click="toggleChatWindow" class="chat-icon">
      <img src="@/assets/chat-icon.png" alt="Chat Icon" />
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
          <button @click="closeChatWindow">X</button>
          <div class="message-count">
            <img class="message-icon" src="@/assets/message.png" alt="" />
            <div class="current-count" v-if="messageCount > 0">
              {{ messageCount }}
            </div>
          </div>
          <div class="nickname">{{ sbUserInfo?.nickname }}</div>
        </header>

        <main>
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
import { applyReactInVue, applyPureReactInVue } from "veaury";
import { ref } from "vue";

import ChatReactComponent from "../react_app/Chat.jsx";
import WeatherForecast from "@/components/weather/WeatherForecast.vue";
import ChatBot from "@/components/chatbot/ChatBot.vue";
import YoutubeVideoPopular from "@/components/youtube/YoutubeVideoPopular.vue";
import YoutubeVideoDetail from "@/components/youtube/YoutubeVideoDetail.vue";

const SENDBIRD_API_KEY = import.meta.env.VITE_SENDBIRD_API_KEY;

export default {
  components: {
    YoutubeVideoDetail,
    YoutubeVideoPopular,
    Chat: applyPureReactInVue(ChatReactComponent),
  },
  setup() {
    //토글
    const isChatOpen = ref(false);

    // 채팅 창 열기/닫기 토글
    const toggleChatWindow = () => {
      isChatOpen.value = true;
    };

    // 채팅 창 닫기
    const closeChatWindow = () => {
      isChatOpen.value = false;
    };

    //로그인이 안되어 있으면 알림

    const token = sessionStorage.getItem("access-token");
    // if (!token) {
    //   alert("로그인 시 이용 가능합니다.");
    // } else {
    let config = {
      APP_ID: null,
      USER_ID: null,
      NICKNAME: null,
    };

    if (token) {
      const name = JSON.parse(atob(token.split(".")[1]))["name"];
      const nickname = JSON.parse(atob(token.split(".")[1]))["nickname"];
      config = {
        APP_ID: SENDBIRD_API_KEY,
        USER_ID: name,
        NICKNAME: nickname,
      };
    } else {
      alert("채팅 기능은 로그인 시 이용 가능합니다.");
    }
    const userRef = ref(null);
    const messageCountRef = ref(null);
    return {
      isChatOpen,
      toggleChatWindow,
      closeChatWindow,
      config: config,
      setSbUserInfo: (user) => {
        userRef.value = user;
      },
      setUnreadMessageCount: (count) => {
        messageCountRef.value = count;
      },
      sbUserInfo: userRef,
      messageCount: messageCountRef,
    };
  },
};
</script>

<style scoped>
/* 채팅 아이콘 스타일 */
.chat-icon {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  background-color: #007bff;
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
  right: 20px;
  bottom: 80px;
  width: 350px;
  height: 500px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  z-index: 9998;
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
</style>
