<template>
  <div>
    <h2>YoutubeView</h2>
    <!-- 검색 -->
    <YoutubeVideoSearch />
    <!-- 상세보기 -->
    <hr />
    <div class="youtube-view">
      <div class="video-detail">
        <YoutubeVideoDetail />
      </div>
      <!-- 리뷰 컴포넌트 -->
      <div class="video-review">
        <YoutubeVideoReview />
        <!-- 리스트 -->
      </div>
    </div>
    <hr />
    <YoutubeVideoList />
  </div>
</template>

<script setup>
import YoutubeVideoSearch from '@/components/youtube/YoutubeVideoSearch.vue'
import YoutubeVideoList from '@/components/youtube/YoutubeVideoList.vue'
import YoutubeVideoDetail from '@/components/youtube/YoutubeVideoDetail.vue'
import YoutubeVideoReview from '@/components/youtube/YoutubeVideoReview.vue'
</script>

<style scoped>
.youtube-view {
  display: flex;
  gap: 20px;
}

.video-detail,
.video-review {
  flex: 1;
}
</style>
