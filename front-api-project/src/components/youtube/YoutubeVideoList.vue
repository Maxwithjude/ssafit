<template>
  <div class="search-results">
    <div class="section-header">
      <h2>검색결과</h2>
      <div class="header-line"></div>
    </div>
 
    <div v-if="store.videoList.length > 0" class="video-list">
      <YoutubeVideoListItem 
        v-for="(video, index) in store.videoList"
        :key="video.id.videoId"
        :video="video"
        class="video-item"
        :style="{ animationDelay: `${index * 0.1}s` }"
      ></YoutubeVideoListItem>
    </div>
 
    <div v-else class="no-results">
      <div class="search-illustration">
        <svg class="search-icon" viewBox="0 0 24 24">
          <path fill="currentColor" d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
        </svg>
        <p>검색 결과가 없습니다.</p>
      </div>
    </div>
  </div>
 </template>
 
 <script setup>
 import { ref } from 'vue';
 import { useYoutubeStore } from '@/stores/youtube';
 import YoutubeVideoListItem from './YoutubeVideoListItem.vue';
 
 const store = useYoutubeStore();
 </script>
 
 <style scoped>
 .search-results {
  padding: 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  min-height: 400px;
 }
 
 .section-header {
  margin-bottom: 2rem;
  position: relative;
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
 
 .video-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 2열 레이아웃 */
  gap: 2rem;
  padding: 1rem;
 }
 
 .video-item {
  animation: slideIn 0.6s ease-out forwards;
  opacity: 0;
 }
 
 @keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
 }
 
 .no-results {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 300px;
  text-align: center;
  color: #64748b;
 }
 
 .search-illustration {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
 }
 
 .search-icon {
  width: 48px;
  height: 48px;
  color: #cbd5e1;
  animation: bounce 2s infinite;
 }
 
 @keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
 }
 
 .no-results p {
  font-size: 1.1rem;
  color: #64748b;
 }
 
 /* 반응형 디자인 */
 @media (max-width: 1024px) {
  .video-list {
    grid-template-columns: 1fr; /* 모바일에서는 1열로 변경 */
    gap: 1.5rem;
  }
 
  .search-results {
    padding: 1.5rem;
  }
 
  .section-header h2 {
    font-size: 1.5rem;
  }
 }
 
 /* 호버 효과 */
 .video-item {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
 }
 
 .video-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
 }
 
 /* 스크롤바 스타일링 */
 .search-results {
  scrollbar-width: thin;
  scrollbar-color: #f97316 #f1f1f1;
 }
 
 .search-results::-webkit-scrollbar {
  width: 6px;
 }
 
 .search-results::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
 }
 
 .search-results::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #f97316, #ec4899);
  border-radius: 3px;
 }
 
 /* 다크모드 지원 */
 @media (prefers-color-scheme: dark) {
  .search-results {
    background: #1a1a1a;
  }
 
  .no-results {
    color: #a1a1aa;
  }
 
  .search-icon {
    color: #4b5563;
  }
 
  .no-results p {
    color: #a1a1aa;
  }
 }
 
 /* 접근성을 위한 모션 감소 */
 @media (prefers-reduced-motion: reduce) {
  .video-item {
    animation: none;
  }
 
  .search-icon {
    animation: none;
  }
 }
 </style>