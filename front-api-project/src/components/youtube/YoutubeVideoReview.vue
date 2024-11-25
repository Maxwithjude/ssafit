<template>
  <v-card class="pa-4" elevation="2">
    <h2 class="text-h5">리뷰</h2>

    <!-- 사용자 정보 -->
    <div v-if="isLoggedIn" class="user-info">
      <p>
        작성자: <strong>{{ currentUser }}</strong>
      </p>
    </div>
    <div v-else>
      <v-alert type="info" class="mb-4">
        로그인이 필요합니다. 리뷰를 작성하려면 로그인하세요.
      </v-alert>
    </div>

    <!-- 리뷰 작성 -->
    <div class="review-form" v-if="isLoggedIn">
      <v-textarea
        v-model="newReviewContent"
        label="리뷰를 작성하세요..."
        outlined
        dense
        rows="3"
        maxlength="200"
      ></v-textarea>
      <div class="d-flex justify-space-between align-center">
        <small>{{ newReviewContent.length }}/200</small>
        <v-btn
          color="primary"
          @click="submitReview"
          :disabled="!newReviewContent.trim()"
        >
          리뷰 등록
        </v-btn>
      </div>
    </div>

    <!-- 리뷰 목록 -->
    <v-divider class="my-4"></v-divider>
    <div class="review-list" v-if="reviews.length > 0">
      <v-card
        class="mb-3"
        v-for="review in reviews"
        :key="review.reviewId"
        outlined
        elevation="0"
      >
        <v-card-title>{{ review.reviewTitle }}</v-card-title>
        <v-card-subtitle>작성자: {{ review.userNickname }}</v-card-subtitle>
        <v-card-text>{{ review.reviewContent }}</v-card-text>
      </v-card>
    </div>
    <div v-else>
      <v-alert type="info">
        리뷰가 없습니다. 첫 번째 리뷰를 작성해보세요!
      </v-alert>
    </div>
  </v-card>
</template>

<script>
import { ref, computed, watch } from "vue";
import { useYoutubeStore } from "@/stores/youtube";
import { useUserStore } from "@/stores/user";

export default {
  name: "YoutubeVideoReview",
  props: {
    videoId: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const youtubeStore = useYoutubeStore();
    const userStore = useUserStore();

    // 로그인 상태 및 사용자 정보
    const isLoggedIn = computed(() => userStore.loginUser !== null);
    const currentUser = computed(() => userStore.loginUser);

    const newReviewContent = ref("");
    const reviews = ref([]);

    // 리뷰 불러오기
    const loadReviews = async () => {
      try {
        reviews.value = await youtubeStore.fetchReviews(props.videoId);
      } catch (error) {
        console.error("리뷰 목록 불러오기 실패:", error);
        alert("리뷰 목록을 가져오지 못했습니다.");
      }
    };

    // 리뷰 제출
    const submitReview = async () => {
      if (!newReviewContent.value.trim()) {
        alert("리뷰 내용을 입력해주세요!");
        return;
      }

      const newReview = {
        reviewTitle: `리뷰_${Date.now()}`,
        reviewContent: newReviewContent.value,
        userNickname: currentUser.value,
      };

      try {
        await youtubeStore.addReview(props.videoId, newReview);
        newReviewContent.value = "";
        await loadReviews();
      } catch (error) {
        console.error("리뷰 등록 실패:", error);
        alert("리뷰 등록에 실패했습니다. 다시 시도해주세요.");
      }
    };

    // 비디오 ID 변경 시 리뷰 갱신
    watch(
      () => props.videoId,
      async (newVideoId) => {
        if (newVideoId) {
          await loadReviews();
        }
      },
      { immediate: true }
    );

    return {
      isLoggedIn,
      currentUser,
      reviews,
      newReviewContent,
      submitReview,
      loadReviews,
    };
  },
};
</script>

<style scoped>
.review-form {
  margin-top: 16px;
}

.review-list {
  margin-top: 16px;
}

.user-info p {
  margin-bottom: 16px;
  font-size: 14px;
  color: #333;
}
</style>
