<template>
  <div class="video-review">
    <h2>리뷰</h2>

    <!-- 사용자 정보 -->
    <div v-if="user" class="user-info">
      <p>
        작성자: <strong>{{ user.userNickname }}</strong>
      </p>
    </div>

    <!-- 리뷰 작성 -->
    <div class="review-form">
      <textarea
        v-model="newReviewContent"
        placeholder="리뷰를 작성하세요..."
      ></textarea>
      <button @click="submitReview">리뷰 등록</button>
    </div>

    <!-- 리뷰 목록 -->
    <div class="review-list">
      <div class="review-item" v-for="review in reviews" :key="review.reviewId">
        <h4>{{ review.reviewTitle }}</h4>
        <p>{{ review.reviewContent }}</p>
        <p>작성자: {{ review.userNickname }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { useYoutubeStore } from '@/stores/youtube'
import { useUserStore } from '@/stores/user'

export default {
  name: 'YoutubeVideoReview',
  props: {
    videoId: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const youtubeStore = useYoutubeStore()
    const userStore = useUserStore()

    const user = userStore.currentUser

    // 새로운 리뷰 작성 상태
    const newReviewContent = ref('')

    // 리뷰 목록 상태
    const reviews = ref([])

    // 선택된 비디오 ID가 변경되면 리뷰 불러오기
    watch(
      () => props.videoId,
      async (videoId) => {
        if (videoId) {
          try {
            reviews.value = await youtubeStore.fetchReviews(videoId)
          } catch (error) {
            console.error('리뷰 목록 불러오기 실패:', error)
            alert('리뷰 목록을 가져오지 못했습니다.')
          }
        }
      },
      { immediate: true }
    )

    // 리뷰 제출
    const submitReview = async () => {
      if (!newReviewContent.value.trim()) {
        alert('리뷰 내용을 입력해주세요!')
        return
      }

      // 리뷰 작성 데이터
      const newReview = {
        reviewTitle: `리뷰_${Date.now()}`, // 임시 제목
        reviewContent: newReviewContent.value,
        userNickname: user.userNickname,
        userId: user.id,
      }

      try {
        await youtubeStore.addReview(props.videoId, newReview)
        newReviewContent.value = '' // 작성된 리뷰 초기화
        reviews.value = await youtubeStore.fetchReviews(props.videoId) // 리뷰 목록 새로고침
      } catch (error) {
        console.error('리뷰 등록 실패:', error)
        alert('리뷰 등록에 실패했습니다. 다시 시도해주세요.')
      }
    }

    return {
      user,
      reviews,
      newReviewContent,
      submitReview,
    }
  },
}
</script>

<style scoped>
.video-review {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-left: 20px;
}

.review-form textarea {
  width: 100%;
  height: 80px;
  margin-bottom: 10px;
}

.review-form button {
  padding: 5px 10px;
  cursor: pointer;
}

.review-list {
  margin-top: 20px;
}

.review-item {
  margin-bottom: 15px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.user-info p {
  margin: 0 0 10px;
  font-size: 14px;
  color: #333;
}
</style>
