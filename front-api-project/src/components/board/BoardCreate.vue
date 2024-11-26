<template>
    <div class="board-create">
      <div class="section-header">
        <h2>게시글 작성</h2>
        <div class="header-line"></div>
      </div>
   
      <div class="create-form">
        <!-- 제목 입력 -->
        <div class="form-group">
          <label for="title">제목</label>
          <input 
            type="text" 
            id="title" 
            v-model="board.title"
            placeholder="제목을 입력하세요"
            class="form-input"
          >
        </div>
   
        <!-- 작성자 입력 -->
        <div class="form-group">
          <label for="writer">작성자</label>
          <input 
            type="text" 
            id="writer" 
            v-model="board.writer"
            placeholder="작성자명을 입력하세요"
            class="form-input"
          >
        </div>
   
        <!-- 내용 입력 -->
        <div class="form-group">
          <label for="content">내용</label>
          <textarea 
            id="content" 
            v-model="board.content"
            placeholder="내용을 입력하세요"
            class="form-textarea"
            rows="10"
          ></textarea>
        </div>
   
        <!-- 버튼 그룹 -->
        <div class="button-group">
          <RouterLink to="/board" class="cancel-btn">
            취소
          </RouterLink>
          <button @click="handleSubmit" class="submit-btn">
            등록하기
            <span class="btn-arrow">→</span>
          </button>
        </div>
      </div>
    </div>
   </template>
   
   <script setup>
   import { useBoardStore } from '@/stores/board';
   import { ref } from 'vue';
   import { useRouter } from 'vue-router';
   
   const store = useBoardStore();
   const router = useRouter();
   
   const board = ref({
    title: '',
    writer: '',
    content: ''
   });
   
   const handleSubmit = async () => {
    // 유효성 검사
    if (!board.value.title.trim()) {
      alert('제목을 입력해주세요.');
      return;
    }
    if (!board.value.writer.trim()) {
      alert('작성자를 입력해주세요.');
      return;
    }
    if (!board.value.content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }
   
    try {
      await store.createBoard(board.value);
      router.push({ name: 'boardList' });
    } catch (error) {
      console.error('게시글 등록 실패:', error);
      alert('게시글 등록에 실패했습니다.');
    }
   };
   </script>
   
   <style scoped>
   .board-create {
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
   
   .create-form {
    background: white;
    border-radius: 12px;
    padding: 2rem;
   }
   
   .form-group {
    margin-bottom: 1.5rem;
   }
   
   .form-group label {
    display: block;
    font-weight: 600;
    color: #1e293b;
    margin-bottom: 0.5rem;
   }
   
   .form-input,
   .form-textarea {
    width: 100%;
    padding: 0.75rem 1rem;
    border: 2px solid #e2e8f0;
    border-radius: 8px;
    font-size: 1rem;
    transition: all 0.3s ease;
   }
   
   .form-input:focus,
   .form-textarea:focus {
    outline: none;
    border-color: #f97316;
    box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.1);
   }
   
   .form-textarea {
    resize: vertical;
    min-height: 150px;
   }
   
   .button-group {
    display: flex;
    justify-content: flex-end;
    gap: 1rem;
    margin-top: 2rem;
   }
   
   .cancel-btn,
   .submit-btn {
    padding: 0.75rem 1.5rem;
    border-radius: 10px;
    font-weight: 600;
    transition: all 0.3s ease;
    cursor: pointer;
    text-decoration: none;
   }
   
   .cancel-btn {
    background: #f1f5f9;
    color: #64748b;
    border: none;
   }
   
   .submit-btn {
    background: linear-gradient(135deg, #f97316, #ec4899);
    color: white;
    border: none;
    display: flex;
    align-items: center;
    gap: 0.5rem;
   }
   
   .btn-arrow {
    transition: transform 0.3s ease;
   }
   
   .submit-btn:hover .btn-arrow {
    transform: translateX(4px);
   }
   
   .cancel-btn:hover,
   .submit-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
   }
   
   /* 플레이스홀더 스타일 */
   ::placeholder {
    color: #94a3b8;
   }
   
   /* 반응형 디자인 */
   @media (max-width: 768px) {
    .board-create {
      padding: 1rem;
      margin: 0 1rem;
    }
   
    .create-form {
      padding: 1rem;
    }
   
    .button-group {
      flex-direction: column;
    }
   
    .cancel-btn,
    .submit-btn {
      width: 100%;
      text-align: center;
      justify-content: center;
    }
   }
   
   /* 다크모드 지원 */
   @media (prefers-color-scheme: dark) {
    .board-create,
    .create-form {
      background: #1a1a1a;
    }
   
    .form-group label {
      color: #e5e5e5;
    }
   
    .form-input,
    .form-textarea {
      background: #2d2d2d;
      border-color: #3f3f46;
      color: #e5e5e5;
    }
   
    .form-input:focus,
    .form-textarea:focus {
      border-color: #f97316;
    }
   
    .cancel-btn {
      background: #2d2d2d;
      color: #a1a1aa;
    }
   
    ::placeholder {
      color: #71717a;
    }
   }
   </style>