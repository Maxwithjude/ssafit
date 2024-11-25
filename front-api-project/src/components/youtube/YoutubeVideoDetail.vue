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
    </div>
 
    <!-- 리뷰 컴포넌트 -->
    <YoutubeVideoReview :videoId="store.selectVideo.id.videoId" class="video-review" />
  </div>
 </template>
 
 <script setup>
 import { useYoutubeStore } from '@/stores/youtube'
 import { computed } from 'vue'
 import YoutubeVideoReview from './YoutubeVideoReview.vue'
 
 const store = useYoutubeStore()
 
 const videoURL = computed(() => {
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
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
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
  margin-top: 1.5rem;
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
 
 .video-review {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #e5e7eb;
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
 }
 
 /* 다크모드 지원 */
 @media (prefers-color-scheme: dark) {
  .video-detail {
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
 
  .video-review {
    border-top-color: #2d2d2d;
  }
 }
 
 /* 로딩 상태를 위한 스켈레톤 스타일 (선택적) */
 .loading {
  animation: pulse 1.5s infinite;
 }
 
 @keyframes pulse {
  0% { opacity: 0.6; }
  50% { opacity: 1; }
  100% { opacity: 0.6; }
 }
 </style>