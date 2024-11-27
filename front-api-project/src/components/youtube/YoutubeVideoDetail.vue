<template>
  <div v-if="store.selectVideo" class="video-detail">
    <div class="section-header">
      <h2>영상 상세보기</h2>
      <div class="header-line"></div>
    </div>

    <div class="video-container">
      <div class="video-wrapper">
        <iframe
          :src="videoURL"
          title="YouTube video player"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          referrerpolicy="strict-origin-when-cross-origin"
          allowfullscreen
        ></iframe>
      </div>

      <div class="video-info">
        <h3 class="video-title">{{ store.selectVideo.snippet.title }}</h3>
        <div class="channel-info">
          <span class="channel-name">{{ store.selectVideo.snippet.channelTitle }}</span>
          <span class="publish-date">{{ formatDate(store.selectVideo.snippet.publishedAt) }}</span>
        </div>
        <p class="video-description">{{ store.selectVideo.snippet.description }}</p>
      </div>

      <!-- 리뷰 컴포넌트 - 영상이 선택되었을 때만 표시 -->
      <div class="video-review-section">
        <!-- <div class="review-header">
          <h3>영상 리뷰</h3>
          <div class="review-line"></div>
        </div> -->
        <YoutubeVideoReview 
          :videoId="store.selectVideo.id.videoId" 
          class="video-review"
        />
      </div>
    </div>
  </div>
  <div v-else class="no-video-selected">
    <div class="no-video-content">
      <svg 
        xmlns="http://www.w3.org/2000/svg" 
        width="48" 
        height="48" 
        viewBox="0 0 24 24" 
        fill="none" 
        stroke="currentColor" 
        stroke-width="2" 
        stroke-linecap="round" 
        stroke-linejoin="round"
      >
        <path d="M21 8v8a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
        <polyline points="10 12 14 12"></polyline>
      </svg>
      <p>영상을 선택하면 상세 정보와 리뷰를 볼 수 있습니다.</p>
    </div>
  </div>
</template>

<script setup>
import { useYoutubeStore } from '@/stores/youtube'
import { computed } from 'vue'
import YoutubeVideoReview from './YoutubeVideoReview.vue'

const store = useYoutubeStore()

const videoURL = computed(() => {
  if (!store.selectVideo) return ''
  const videoSrc = store.selectVideo.id.videoId
  return `https://www.youtube.com/embed/${videoSrc}`
})

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return new Date(dateString).toLocaleDateString('ko-KR', options)
}
</script>
 
<style scoped>
.video-detail {
 padding: 2rem;
 background: white;
 border-radius: 12px;
 box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
 overflow: hidden;
 animation: fadeIn 0.5s ease-out;
}

@keyframes fadeIn {
 from {
   opacity: 0;
   transform: translateY(20px);
 }
 to {
   opacity: 1;
   transform: translateY(0);
 }
}

.section-header {
 margin-bottom: 2rem;
}

.section-header h2 {
 font-size: 1.75rem;
 font-weight: 700;
 color: #1e293b;
 margin-bottom: 1rem;
 display: inline-block;
 background: linear-gradient(135deg, #f97316, #ec4899);
 background-clip: text;
 -webkit-background-clip: text;
 -webkit-text-fill-color: transparent;
}

.header-line {
 height: 3px;
 width: 60px;
 background: linear-gradient(135deg, #f97316, #ec4899);
 border-radius: 2px;
}

.video-container {
 margin-bottom: 2rem;
}

.video-wrapper {
 position: relative;
 padding-bottom: 56.25%; /* 16:9 비율 */
 height: 0;
 overflow: hidden;
 border-radius: 15px;
 box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.video-wrapper iframe {
 position: absolute;
 top: 0;
 left: 0;
 width: 100%;
 height: 100%;
 border: none;
}

.video-info {
 padding: 1.5rem;
 background: #f8f9fa;
 border-radius: 15px;
 margin: 1.5rem 0;
}

.video-title {
 font-size: 1.25rem;
 font-weight: 600;
 color: #1e293b;
 margin-bottom: 1rem;
 line-height: 1.4;
}

.channel-info {
 display: flex;
 align-items: center;
 gap: 1rem;
 margin-bottom: 1rem;
}

.channel-name {
 color: #f97316;
 font-weight: 500;
}

.publish-date {
 color: #64748b;
 font-size: 0.9rem;
}

.video-description {
 color: #4b5563;
 line-height: 1.6;
 font-size: 0.95rem;
 max-height: 150px;
 overflow-y: auto;
 padding-right: 1rem;
}

/* 리뷰 섹션 스타일링 */
.video-review-section {
 margin-top: 2rem;
 padding-top: 2rem;
 border-top: 1px solid #e5e7eb;
}

.review-header {
 margin-bottom: 1.5rem;
}

.review-header h3 {
 font-size: 1.5rem;
 font-weight: 600;
 color: #1e293b;
 margin-bottom: 0.5rem;
}

.review-line {
 height: 2px;
 width: 40px;
 background: linear-gradient(135deg, #f97316, #ec4899);
 border-radius: 2px;
}

/* 영상 미선택 상태 스타일링 */
.no-video-selected {
 padding: 4rem 2rem;
 text-align: center;
 background: white;
 border-radius: 12px;
 box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.no-video-content {
 display: flex;
 flex-direction: column;
 align-items: center;
 gap: 1rem;
 color: #64748b;
}

.no-video-content svg {
 color: #cbd5e1;
}

.no-video-content p {
 font-size: 1.1rem;
}

/* 스크롤바 스타일링 */
.video-description::-webkit-scrollbar {
 width: 4px;
}

.video-description::-webkit-scrollbar-track {
 background: #f1f1f1;
 border-radius: 2px;
}

.video-description::-webkit-scrollbar-thumb {
 background: linear-gradient(135deg, #f97316, #ec4899);
 border-radius: 2px;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
 .video-detail {
   padding: 1rem;
 }

 .section-header h2 {
   font-size: 1.5rem;
 }

 .video-info {
   padding: 1rem;
 }

 .video-title {
   font-size: 1.1rem;
 }

 .channel-info {
   flex-direction: column;
   align-items: flex-start;
   gap: 0.5rem;
 }

 .review-header h3 {
   font-size: 1.3rem;
 }

 .no-video-selected {
   padding: 2rem 1rem;
 }

 .no-video-content p {
   font-size: 1rem;
 }
}

/* 다크모드 지원 */
@media (prefers-color-scheme: dark) {
 .video-detail,
 .no-video-selected {
   background: #1a1a1a;
 }

 .video-info {
   background: #2d2d2d;
 }

 .video-title {
   color: #e5e5e5;
 }

 .video-description {
   color: #a1a1aa;
 }

 .review-header h3 {
   color: #e5e5e5;
 }

 .no-video-content {
   color: #a1a1aa;
 }

 .no-video-content svg {
   color: #4b5563;
 }
}

/* 애니메이션 */
@keyframes pulse {
 0% { opacity: 0.6; }
 50% { opacity: 1; }
 100% { opacity: 0.6; }
}

.loading {
 animation: pulse 1.5s infinite;
}
</style>