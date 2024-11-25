<template>
  <div class="search-container">
    <div class="section-header">
      <h2>러닝 영상 검색</h2>
      <div class="header-line"></div>
    </div>
 
    <div class="search-form">
      <div class="search-input-wrapper">
        <input 
          type="text" 
          v-model.trim="keyword" 
          @keyup.enter="search"
          placeholder="검색어를 입력하세요"
          class="search-input"
        />
        <span v-if="keyword" class="clear-button" @click="clearSearch">×</span>
      </div>
      
      <button @click="search" class="search-button">
        <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
        </svg>
        <span>검색</span>
      </button>
    </div>
 
    <div class="search-suggestions" v-if="!keyword">
      <p class="suggestion-title">추천 검색어</p>
      <div class="suggestion-tags">
        <button 
          v-for="tag in suggestionTags" 
          :key="tag"
          @click="searchTag(tag)"
          class="suggestion-tag"
        >
          {{ tag }}
        </button>
      </div>
    </div>
  </div>
 </template>
 
 <script setup>
 import { ref } from 'vue';
 import { useYoutubeStore } from '@/stores/youtube';
 
 const store = useYoutubeStore();
 const keyword = ref('');
 
 const suggestionTags = [
  '러닝 초보',
  '러닝 자세',
  '러닝화 추천',
  '러닝 다이어트',
  '러닝 워밍업'
 ];
 
 const search = function () {
  if (keyword.value.trim()) {
    store.youtubeSearch(keyword.value);
  }
 };
 
 const searchTag = function (tag) {
  keyword.value = tag;
  search();
 };
 
 const clearSearch = function () {
  keyword.value = '';
 };
 </script>
 
 <style scoped>
 .search-container {
  padding: 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
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
 
 .search-form {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
 }
 
 .search-input-wrapper {
  position: relative;
  flex: 1;
 }
 
 .search-input {
  width: 100%;
  padding: 1rem 1.5rem;
  font-size: 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  transition: all 0.3s ease;
  color: #1e293b;
 }
 
 .search-input:focus {
  outline: none;
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.1);
 }
 
 .search-input::placeholder {
  color: #94a3b8;
 }
 
 .clear-button {
  position: absolute;
  right: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: #94a3b8;
  cursor: pointer;
  font-size: 1.25rem;
  padding: 0.25rem;
  transition: color 0.3s ease;
 }
 
 .clear-button:hover {
  color: #64748b;
 }
 
 .search-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0 1.5rem;
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
 }
 
 .search-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
 }
 
 .search-icon {
  width: 20px;
  height: 20px;
 }
 
 .search-suggestions {
  animation: fadeIn 0.3s ease-out;
 }
 
 .suggestion-title {
  font-size: 0.95rem;
  color: #64748b;
  margin-bottom: 1rem;
 }
 
 .suggestion-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
 }
 
 .suggestion-tag {
  padding: 0.5rem 1rem;
  background: #f8fafc;
  border: none;
  border-radius: 20px;
  color: #1e293b;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
 }
 
 .suggestion-tag:hover {
  background: #f1f5f9;
  transform: translateY(-2px);
 }
 
 @keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
 }
 
 /* 반응형 디자인 */
 @media (max-width: 640px) {
  .search-container {
    padding: 1.5rem;
  }
 
  .search-form {
    flex-direction: column;
  }
 
  .search-button {
    width: 100%;
    justify-content: center;
    padding: 1rem;
  }
 
  .suggestion-tags {
    gap: 0.5rem;
  }
 
  .suggestion-tag {
    padding: 0.5rem 0.75rem;
    font-size: 0.85rem;
  }
 }
 
 /* 다크모드 지원 */
 @media (prefers-color-scheme: dark) {
  .search-container {
    background: #1a1a1a;
  }
 
  .search-input {
    background: #2d2d2d;
    border-color: #3f3f46;
    color: #e5e5e5;
  }
 
  .search-input::placeholder {
    color: #71717a;
  }
 
  .suggestion-title {
    color: #a1a1aa;
  }
 
  .suggestion-tag {
    background: #2d2d2d;
    color: #e5e5e5;
  }
 
  .suggestion-tag:hover {
    background: #3f3f46;
  }
 }
 </style>