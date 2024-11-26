<template>
    <div class="board-container">
      <div class="section-header">
        <h2>러닝크루 모집</h2>
        <div class="header-line"></div>
      </div>
   
      <div class="board-content">
        <!-- 검색 입력 -->
        <BoardSearchInput class="search-input" />
   
        <!-- 게시글 목록 -->
        <div class="table-container">
          <table class="board-table">
            <thead>
              <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>등록일</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="board in store.boardList" 
                  :key="board.id"
                  class="board-row"
              >
                <td>{{ board.id }}</td>
                <td class="title-cell">
                  <RouterLink :to="`/board/${board.id}`" class="board-title">
                    {{ board.title }}
                  </RouterLink>
                </td>
                <td>{{ board.writer }}</td>
                <td>{{ board.viewCnt }}</td>
                <td>{{ formatDate(board.regDate) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
   
        <!-- 글쓰기 버튼 -->
        <div class="button-container">
          <RouterLink to="/board/create" class="create-button">
            글쓰기
            <span class="button-arrow">→</span>
          </RouterLink>
        </div>
      </div>
    </div>
   </template>
   
   <script setup>
   import { useBoardStore } from '@/stores/board';
   import { onMounted } from 'vue';
   import BoardSearchInput from './BoardSearchInput.vue';
   
   const store = useBoardStore();
   
   onMounted(() => {
    store.getBoardList();
   })
   
   const formatDate = (dateString) => {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    }).format(date);
   };
   </script>
   
   <style scoped>
   .board-container {
    padding: 2rem;
    background: white;
    border-radius: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    max-width: 1200px;
    margin: 0 auto;
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
   
   .board-content {
    position: relative;
   }
   
   .search-input {
    margin-bottom: 2rem;
   }
   
   .table-container {
    overflow-x: auto;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
   }
   
   .board-table {
    width: 100%;
    border-collapse: collapse;
    white-space: nowrap;
   }
   
   .board-table th,
   .board-table td {
    padding: 1rem;
    text-align: center;
    border-bottom: 1px solid #e2e8f0;
   }
   
   .board-table th {
    background: #f8fafc;
    font-weight: 600;
    color: #1e293b;
   }
   
   .board-row {
    transition: background-color 0.2s ease;
   }
   
   .board-row:hover {
    background-color: #f8fafc;
   }
   
   .title-cell {
    text-align: left;
    max-width: 400px;
   }
   
   .board-title {
    color: #1e293b;
    text-decoration: none;
    transition: color 0.2s ease;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
   }
   
   .board-title:hover {
    color: #f97316;
   }
   
   .button-container {
    margin-top: 2rem;
    display: flex;
    justify-content: flex-end;
   }
   
   .create-button {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1.5rem;
    background: linear-gradient(135deg, #f97316, #ec4899);
    color: white;
    text-decoration: none;
    border-radius: 10px;
    font-weight: 600;
    transition: all 0.3s ease;
   }
   
   .create-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(249, 115, 22, 0.15);
   }
   
   .button-arrow {
    transition: transform 0.3s ease;
   }
   
   .create-button:hover .button-arrow {
    transform: translateX(4px);
   }
   
   /* 반응형 디자인 */
   @media (max-width: 768px) {
    .board-container {
      padding: 1rem;
      margin: 0 1rem;
    }
   
    .section-header h2 {
      font-size: 1.5rem;
    }
   
    .board-table th,
    .board-table td {
      padding: 0.75rem 0.5rem;
      font-size: 0.9rem;
    }
   
    .title-cell {
      max-width: 200px;
    }
   }
   
   /* 다크모드 지원 */
   @media (prefers-color-scheme: dark) {
    .board-container {
      background: #1a1a1a;
    }
   
    .table-container {
      background: #1a1a1a;
    }
   
    .board-table th {
      background: #2d2d2d;
      color: #e5e5e5;
    }
   
    .board-table td {
      border-bottom-color: #2d2d2d;
      color: #e5e5e5;
    }
   
    .board-row:hover {
      background-color: #2d2d2d;
    }
   
    .board-title {
      color: #e5e5e5;
    }
   
    .board-title:hover {
      color: #f97316;
    }
   }
   
   /* 애니메이션 */
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
   
   .board-container {
    animation: fadeIn 0.5s ease-out;
   }
   </style>