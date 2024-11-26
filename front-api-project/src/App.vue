<template>
  <Suspense>
    <template #default>
      <TheHeaderNav />
    </template>
    <template #fallback>
      <div>Loading Header...</div>
      <!-- 로딩 중 표시 -->
    </template>
  </Suspense>

  <router-view />
</template>

<script setup>
import { onMounted } from 'vue'
import { defineAsyncComponent } from 'vue'

// 헤더 컴포넌트 비동기 로드
const TheHeaderNav = defineAsyncComponent(() =>
  import('./components/common/TheHeaderNav.vue')
)

// SendBird 챗봇 로드
onMounted(() => {
  // 챗봇 컨테이너 생성
  const div = document.createElement('div')
  div.id = 'aichatbot'
  document.body.appendChild(div)

  // SendBird 챗봇 설정
  const script = document.createElement('script')
  script.type = 'module'
  script.defer = true
  script.src = 'https://aichatbot.sendbird.com/index.js'

  // 챗봇 초기화
  script.onload = () => {
    window.chatbotConfig = [
      'F9D5B2A2-EAA9-4AE7-A8F8-4F5AEB37EE0D', // 챗봇 ID
      'iRv0h15cqVQZbL0wjKrnH', // 클라이언트 키
      {
        apiHost: 'https://api-cf-ap-2.sendbird.com', // API 호스트
      },
    ]
  }

  // DOM에 스크립트 추가
  document.head.appendChild(script)
})
</script>

<style></style>
