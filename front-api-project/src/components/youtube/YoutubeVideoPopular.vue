<template>
  <div>
    <h4>운동 인기 동영상</h4>
    <ul v-if="store.popularVideoList.length > 0">
      <!-- 컴포넌트를 반복 -->
      <YoutubeVideoListItem
        v-for="video in store.popularVideoList"
        :key="video.id.videoId"
        :video="video"
      ></YoutubeVideoListItem>
    </ul>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useYoutubeStore } from "@/stores/youtube";
import YoutubeVideoListItem from "./YoutubeVideoListItem.vue";
const store = useYoutubeStore();
onMounted(() => {
  store.searchPopular("운동");
});
</script>

<style scoped></style>
