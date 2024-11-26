<template>
    <div class="board-detail">
      <div class="section-header">
        <h2>게시글 상세</h2>
        <div class="header-line"></div>
      </div>
   
      <div class="detail-container">
        <!-- 게시글 헤더 -->
        <div class="post-header">
          <h1 class="post-title">{{ store.board.title }}</h1>
          <div class="post-info">
            <div class="info-left">
              <span class="writer">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
                {{ store.board.writer }}
              </span>
              <span class="date">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                  <line x1="16" y1="2" x2="16" y2="6"></line>
                  <line x1="8" y1="2" x2="8" y2="6"></line>
                  <line x1="3" y1="10" x2="21" y2="10"></line>
                </svg>
                {{ formatDate(store.board.regDate) }}
              </span>
            </div>
            <div class="info-right">
              <span class="views">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                  <circle cx="12" cy="12" r="3"></circle>
                </svg>
                조회수 {{ store.board.viewCnt }}
              </span>
            </div>
          </div>
        </div>
   
        <!-- 게시글 내용 -->
        <div class="post-content">
          {{ store.board.content }}
        </div>
   
        <!-- 버튼 그룹 -->
        <div class="button-group">
          <RouterLink to="/board" class="btn btn-secondary">
            목록으로
          </RouterLink>
          <div class="action-buttons">
            <button @click="updateBoard" class="btn btn-primary">수정</button>
            <button @click="confirmDelete" class="btn btn-danger">삭제</button>
          </div>
        </div>
      </div>
    </div>
   </template>
   
   <script setup>
   import { useBoardStore } from '@/stores/board';
   import { onMounted } from 'vue';
   import { useRoute, useRouter } from 'vue-router';
   import axios from 'axios';
   
   const store = useBoardStore();
   const route = useRoute();
   const router = useRouter();
   
   onMounted(() => {
    store.getBoard(route.params.id);
   });
   
   const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    }).format(date);
   };
   
   const confirmDelete = () => {
    if (confirm('정말 삭제하시겠습니까?')) {
      deleteBoard();
    }
   };
   
   const deleteBoard = async () => {
    try {
      await axios.delete(`http://localhost:8080/api-board/board/${route.params.id}`);
      router.push({ name: 'boardList' });
    } catch (error) {
      console.error('게시글 삭제 실패:', error);
      alert('게시글 삭제에 실패했습니다.');
    }
   };
   
   const updateBoard = () => {
    router.push({ name: 'boardUpdate' });
   };
   </script>
   
   <style scoped>
   .board-detail {
    padding: 2rem;
    background: white;
    border-radius: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    max-width: 1200px;
    margin: 0 auto;
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
   
   .detail-container {
    background: white;
    border-radius: 12px;
    overflow: hidden;
   }
   
   .post-header {
    padding: 2rem;
    border-bottom: 1px solid #e2e8f0;
   }
   
   .post-title {
    font-size: 1.75rem;
    font-weight: 700;
    color: #1e293b;
    margin-bottom: 1rem;
   }
   
   .post-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #64748b;
    font-size: 0.95rem;
   }
   
   .info-left {
    display: flex;
    align-items: center;
    gap: 1.5rem;
   }
   
   .writer, .date, .views {
    display: flex;
    align-items: center;
    gap: 0.5rem;
   }
   
   .post-content {
    padding: 2rem;
    line-height: 1.8;
    color: #1e293b;
    min-height: 200px;
   }
   
   .button-group {
    padding: 1.5rem 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #e2e8f0;
   }
   
   .action-buttons {
    display: flex;
    gap: 1rem;
   }
   
   .btn {
    padding: 0.75rem 1.5rem;
    border-radius: 10px;
    font-weight: 600;
    transition: all 0.3s ease;
    border: none;
    cursor: pointer;
    text-decoration: none;
   }
   
   .btn-secondary {
    background: #f1f5f9;
    color: #64748b;
   }
   
   .btn-primary {
    background: linear-gradient(135deg, #f97316, #ec4899);
    color: white;
   }
   
   .btn-danger {
    background: #ef4444;
    color: white;
   }
   
   .btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
   }
   
   /* 반응형 디자인 */
   @media (max-width: 768px) {
    .board-detail {
      padding: 1rem;
      margin: 0 1rem;
    }
   
    .post-header {
      padding: 1.5rem;
    }
   
    .post-title {
      font-size: 1.5rem;
    }
   
    .post-info {
      flex-direction: column;
      align-items: flex-start;
      gap: 1rem;
    }
   
    .post-content {
      padding: 1.5rem;
    }
   
    .button-group {
      flex-direction: column-reverse;
      gap: 1rem;
    }
   
    .action-buttons {
      width: 100%;
    }
   
    .btn {
      width: 100%;
      text-align: center;
    }
   }
   
   /* 다크모드 지원 */
   @media (prefers-color-scheme: dark) {
    .board-detail {
      background: #1a1a1a;
    }
   
    .detail-container {
      background: #1a1a1a;
    }
   
    .post-title {
      color: #e5e5e5;
    }
   
    .post-content {
      color: #e5e5e5;
    }
   
    .post-header,
    .button-group {
      border-color: #2d2d2d;
    }
   
    .btn-secondary {
      background: #2d2d2d;
      color: #a1a1aa;
    }
   }
   </style>