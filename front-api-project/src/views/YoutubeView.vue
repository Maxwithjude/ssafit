<!-- YoutubeView.vue -->
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
    </div>
    <hr />
    <YoutubeVideoList />
  </div>
</template>

<script setup>
import YoutubeVideoSearch from '@/components/youtube/YoutubeVideoSearch.vue'
import YoutubeVideoList from '@/components/youtube/YoutubeVideoList.vue'
import YoutubeVideoDetail from '@/components/youtube/YoutubeVideoDetail.vue'
</script>

<style scoped>
.youtube-view {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem;
}
</style>