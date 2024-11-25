<template>
  <div class="review-container">
    <div class="review-header">
      <h2>영상 리뷰</h2>
      <div class="header-line"></div>
    </div>
 
    <!-- 사용자 정보 -->
    <div v-if="isLoggedIn" class="user-info">
      <div class="user-avatar">{{ currentUser.charAt(0) }}</div>
      <p>작성자: <strong>{{ currentUser }}</strong></p>
    </div>
    <div v-else class="login-alert">
      <div class="alert-content">
        <svg class="alert-icon" viewBox="0 0 24 24">
          <path fill="currentColor" d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"/>
        </svg>
        <p>로그인이 필요합니다. 리뷰를 작성하려면 로그인하세요.</p>
      </div>
    </div>
 
    <!-- 리뷰 작성 폼 -->
    <div v-if="isLoggedIn" class="review-form">
      <textarea
        v-model="newReviewContent"
        placeholder="리뷰를 작성하세요..."
        maxlength="200"
        rows="3"
        class="review-textarea"
      ></textarea>
      <div class="form-footer">
        <span class="char-count">{{ newReviewContent.length }}/200</span>
        <button 
          @click="submitReview"
          :disabled="!newReviewContent.trim()"
          class="submit-button"
        >
          리뷰 등록
        </button>
      </div>
    </div>
 
    <!-- 리뷰 목록 -->
    <div class="reviews-divider"></div>
    <div v-if="reviews.length > 0" class="review-list">
      <div 
        v-for="(review, index) in reviews" 
        :key="review.reviewId"
        class="review-card"
        :style="{ animationDelay: `${index * 0.1}s` }"
      >
        <div class="review-header">
          <div class="reviewer-info">
            <div class="reviewer-avatar">{{ review.userNickname.charAt(0) }}</div>
            <div>
              <h3 class="reviewer-name">{{ review.userNickname }}</h3>
              <span class="review-date">{{ formatDate(review.reviewDate) }}</span>
            </div>
          </div>
        </div>
        <p class="review-content">{{ review.reviewContent }}</p>
      </div>
    </div>
    <div v-else class="no-reviews">
      <div class="no-reviews-content">
        <svg class="empty-icon" viewBox="0 0 24 24">
          <path fill="currentColor" d="M20 2H4c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zM9 19H7v-2h2v2zm0-4H7v-2h2v2zm0-4H7V9h2v2zm0-4H7V5h2v2zm10 12h-8v-2h8v2zm0-4h-8v-2h8v2zm0-4h-8V9h8v2zm0-4h-8V5h8v2z"/>
        </svg>
        <p>첫 번째 리뷰를 작성해보세요!</p>
      </div>
    </div>
  </div>
 </template>
 
 <script setup>
 import { ref, computed, watch } from "vue";
 import { useYoutubeStore } from "@/stores/youtube";
 import { useUserStore } from "@/stores/user";
 
 const props = defineProps({
  videoId: {
    type: String,
    required: true,
  },
 });
 
 const youtubeStore = useYoutubeStore();
 const userStore = useUserStore();
 
 const isLoggedIn = computed(() => userStore.loginUser !== null);
 const currentUser = computed(() => userStore.loginUser);
 
 const newReviewContent = ref("");
 const reviews = ref([]);
 
 const formatDate = (date) => {
  return new Date(date).toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
 };
 
 const loadReviews = async () => {
  try {
    reviews.value = await youtubeStore.fetchReviews(props.videoId);
  } catch (error) {
    console.error("리뷰 목록 불러오기 실패:", error);
  }
 };
 
 const submitReview = async () => {
  if (!newReviewContent.value.trim()) return;
 
  const newReview = {
    reviewTitle: `리뷰_${Date.now()}`,
    reviewContent: newReviewContent.value,
    userNickname: currentUser.value,
    reviewDate: new Date().toISOString(),
  };
 
  try {
    await youtubeStore.addReview(props.videoId, newReview);
    newReviewContent.value = "";
    await loadReviews();
  } catch (error) {
    console.error("리뷰 등록 실패:", error);
  }
 };
 
 watch(() => props.videoId, async (newVideoId) => {
  if (newVideoId) {
    await loadReviews();
  }
 }, { immediate: true });
 </script>
 
 <style scoped>
 .review-container {
  background: white;
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
 }
 
 .review-header {
  margin-bottom: 2rem;
 }
 
 .review-header h2 {
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
 
 .user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
 }
 
 .user-avatar {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #f97316, #ec4899);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
  text-transform: uppercase;
 }
 
 .login-alert {
  background: #fff7ed;
  border-radius: 12px;
  padding: 1rem;
  margin-bottom: 1.5rem;
 }
 
 .alert-content {
  display: flex;
  align-items: center;
  gap: 1rem;
  color: #ea580c;
 }
 
 .alert-icon {
  width: 24px;
  height: 24px;
 }
 
 .review-form {
  margin-bottom: 2rem;
 }
 
 .review-textarea {
  width: 100%;
  padding: 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  resize: vertical;
  min-height: 100px;
  font-size: 1rem;
  transition: all 0.3s ease;
 }
 
 .review-textarea:focus {
  outline: none;
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.1);
 }
 
 .form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 0.5rem;
 }
 
 .char-count {
  color: #64748b;
  font-size: 0.9rem;
 }
 
 .submit-button {
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
 }
 
 .submit-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
 }
 
 .submit-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
 }
 
 .reviews-divider {
  height: 1px;
  background: #e2e8f0;
  margin: 2rem 0;
 }
 
 .review-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
 }
 
 .review-card {
  background: #f8fafc;
  border-radius: 12px;
  padding: 1.5rem;
  animation: slideIn 0.5s ease-out forwards;
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
 
 .reviewer-info {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
 }
 
 .reviewer-avatar {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #f97316, #ec4899);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 0.9rem;
  font-weight: bold;
 }
 
 .reviewer-name {
  font-weight: 600;
  color: #1e293b;
  margin: 0;
 }
 
 .review-date {
  font-size: 0.85rem;
  color: #64748b;
 }
 
 .review-content {
  color: #1e293b;
  line-height: 1.6;
  margin: 0;
 }
 
 .no-reviews {
  text-align: center;
  padding: 3rem 0;
  color: #64748b;
 }
 
 .empty-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 1rem;
  color: #cbd5e1;
 }
 
 @media (max-width: 640px) {
  .review-container {
    padding: 1.5rem;
  }
 
  .review-header h2 {
    font-size: 1.5rem;
  }
 
  .submit-button {
    padding: 0.5rem 1rem;
  }
 }
 </style>