<template>
  <div class="popular-videos">
    <div class="section-header">
      <h2>운동 인기 동영상</h2>
      <div class="header-line"></div>
    </div>
    
    <div v-if="store.popularVideoList.length > 0" class="video-grid">
      <YoutubeVideoListItem
        v-for="(video, index) in store.popularVideoList"
        :key="video.id.videoId"
        :video="video"
        class="video-item"
        :style="{ animationDelay: `${index * 0.2}s` }"
      ></YoutubeVideoListItem>
    </div>
    
    <div v-else class="loading-state">
      <div class="loading-spinner"></div>
      <p>인기 동영상을 불러오는 중입니다...</p>
    </div>
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

<style scoped>
.popular-videos {
  padding: 2rem;
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.section-header {
  margin-bottom: 2rem;
  position: relative;
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

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  animation: fadeIn 0.5s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.video-item {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.video-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.loading-state {
  text-align: center;
  padding: 3rem;
  color: #64748b;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  margin: 0 auto 1rem;
  border: 3px solid #f3f4f6;
  border-top: 3px solid #f97316;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 반응형 디자인 */
@media (max-width: 1200px) {
  .popular-videos {
    padding: 1.5rem;
  }

  .section-header h2 {
    font-size: 1.5rem;
  }

  .video-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
}

/* 다크모드 대응 (선택적) */
@media (prefers-color-scheme: dark) {
  .popular-videos {
    background: #1a1a1a;
  }

  .section-header h2 {
    background: linear-gradient(135deg, #f97316, #ec4899);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }

  .loading-state {
    color: #a1a1aa;
  }

  .loading-spinner {
    border-color: #2d2d2d;
    border-top-color: #f97316;
  }
}
</style>