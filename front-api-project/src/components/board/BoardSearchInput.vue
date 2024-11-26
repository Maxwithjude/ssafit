<template>
    <div class="search-container">
      <div class="search-form">
        <!-- 검색 기준 -->
        <div class="form-group">
          <label for="searchKey">검색 기준</label>
          <select 
            id="searchKey"
            v-model="searchInfo.key"
            class="form-select"
          >
            <option value="none">없음</option>
            <option value="writer">작성자</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
          </select>
        </div>
   
        <!-- 검색어 입력 -->
        <div class="form-group">
          <label for="searchWord">검색어</label>
          <input 
            type="text" 
            id="searchWord"
            v-model="searchInfo.word"
            placeholder="검색어를 입력하세요"
            class="form-input"
          />
        </div>
   
        <!-- 정렬 기준 -->
        <div class="form-group">
          <label for="orderBy">정렬 기준</label>
          <select 
            id="orderBy"
            v-model="searchInfo.orderBy"
            class="form-select"
          >
            <option value="none">없음</option>
            <option value="writer">작성자</option>
            <option value="title">제목</option>
            <option value="view_cnt">조회수</option>
          </select>
        </div>
   
        <!-- 정렬 방향 -->
        <div class="form-group">
          <label for="orderDir">정렬 방향</label>
          <select 
            id="orderDir"
            v-model="searchInfo.orderByDir"
            class="form-select"
          >
            <option value="asc">오름차순</option>
            <option value="desc">내림차순</option>
          </select>
        </div>
   
        <!-- 검색 버튼 -->
        <div class="form-group button-group">
          <button @click="searchBoardList" class="search-button">
            <svg 
              xmlns="http://www.w3.org/2000/svg" 
              width="20" 
              height="20" 
              viewBox="0 0 24 24" 
              fill="none" 
              stroke="currentColor" 
              stroke-width="2" 
              stroke-linecap="round" 
              stroke-linejoin="round"
              class="search-icon"
            >
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
            검색
          </button>
        </div>
      </div>
    </div>
   </template>
   
   <script setup>
   import { useBoardStore } from '@/stores/board';
   import { ref } from 'vue';
   
   const searchInfo = ref({
    key: 'none',
    word: '',
    orderBy: 'none',
    orderByDir: 'asc'
   });
   
   const store = useBoardStore();
   
   const searchBoardList = function() {
    store.searchBoardList(searchInfo.value);
   };
   </script>
   
   <style scoped>
   .search-container {
    margin-bottom: 2rem;
    background: white;
    border-radius: 12px;
    padding: 1.5rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
   }
   
   .search-form {
    display: flex;
    gap: 1rem;
    align-items: flex-end;
    flex-wrap: wrap;
   }
   
   .form-group {
    flex: 1;
    min-width: 200px;
   }
   
   .button-group {
    flex: 0 0 auto;
    min-width: auto;
   }
   
   label {
    display: block;
    font-size: 0.9rem;
    font-weight: 500;
    color: #64748b;
    margin-bottom: 0.5rem;
   }
   
   .form-select,
   .form-input {
    width: 100%;
    padding: 0.625rem 1rem;
    border: 2px solid #e2e8f0;
    border-radius: 8px;
    font-size: 0.95rem;
    color: #1e293b;
    background-color: white;
    transition: all 0.3s ease;
   }
   
   .form-select {
    appearance: none;
    background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23475569' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M6 9l6 6 6-6'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 1rem center;
    background-size: 16px;
    padding-right: 2.5rem;
   }
   
   .form-select:focus,
   .form-input:focus {
    outline: none;
    border-color: #f97316;
    box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.1);
   }
   
   .search-button {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.625rem 1.5rem;
    background: linear-gradient(135deg, #f97316, #ec4899);
    color: white;
    border: none;
    border-radius: 8px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
   }
   
   .search-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(249, 115, 22, 0.15);
   }
   
   .search-icon {
    transition: transform 0.3s ease;
   }
   
   .search-button:hover .search-icon {
    transform: scale(1.1);
   }
   
   /* 반응형 디자인 */
   @media (max-width: 768px) {
    .search-form {
      flex-direction: column;
    }
   
    .form-group {
      width: 100%;
    }
   
    .search-button {
      width: 100%;
      justify-content: center;
    }
   }
   
   /* 다크모드 지원 */
   @media (prefers-color-scheme: dark) {
    .search-container {
      background: #1a1a1a;
    }
   
    label {
      color: #a1a1aa;
    }
   
    .form-select,
    .form-input {
      background-color: #2d2d2d;
      border-color: #3f3f46;
      color: #e5e5e5;
    }
   
    .form-select {
      background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23a1a1aa' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M6 9l6 6 6-6'/%3E%3C/svg%3E");
    }
   
    .form-select:focus,
    .form-input:focus {
      border-color: #f97316;
    }
   }
   
   /* 플레이스홀더 스타일 */
   ::placeholder {
    color: #94a3b8;
   }
   
   @media (prefers-color-scheme: dark) {
    ::placeholder {
      color: #71717a;
    }
   }
   </style>