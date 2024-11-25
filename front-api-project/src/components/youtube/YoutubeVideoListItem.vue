<template>
  <div class="video-item" @click="clickVideo">
    <div class="thumbnail-container">
      <img 
        :src="video.snippet.thumbnails.medium.url" 
        :alt="videoTitle"
        class="video-thumbnail"
      >
      <div class="play-overlay">
        <svg class="play-icon" viewBox="0 0 24 24">
          <path fill="currentColor" d="M8 5v14l11-7z"/>
        </svg>
      </div>
    </div>
    
    <div class="video-info">
      <h3 class="video-title">{{ videoTitle }}</h3>
      <div class="video-meta">
        <p class="channel-name">{{ video.snippet.channelTitle }}</p>
        <p class="video-date">{{ formatDate(video.snippet.publishedAt) }}</p>
      </div>
      <div class="video-stats">
        <span class="views">조회수 1.2만회</span>
        <button class="like-button" @click.stop="toggleLike">
          <svg class="heart-icon" :class="{ 'liked': isLiked }" viewBox="0 0 24 24">
            <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
          </svg>
          찜하기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useYoutubeStore } from '@/stores/youtube';
import _ from 'lodash'

const store = useYoutubeStore();
const isLiked = ref(false);

const props = defineProps({
  video: Object
})

const videoTitle = computed(() => {
  return _.unescape(props.video.snippet.title)
})

const clickVideo = function() {
  store.clickVideo(props.video)
}

const toggleLike = () => {
  isLiked.value = !isLiked.value;
}

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
}
</script>

<style scoped>
.video-item {
  background: white;
  border-radius: 15px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 1.5rem;
  padding: 1rem;
  animation: slideIn 0.5s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.video-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.thumbnail-container {
  position: relative;
  flex: 0 0 200px;
  height: 150px;
  border-radius: 10px;
  overflow: hidden;
}

.video-thumbnail {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.video-item:hover .video-thumbnail {
  transform: scale(1.05);
}

.play-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.video-item:hover .play-overlay {
  opacity: 1;
}

.play-icon {
  width: 48px;
  height: 48px;
  color: white;
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.3));
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.1); }
  100% { transform: scale(1); }
}

.video-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.video-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 0.5rem;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.video-meta {
  margin-bottom: auto;
}

.channel-name {
  font-size: 0.9rem;
  color: #f97316;
  margin: 0;
  margin-bottom: 0.25rem;
}

.video-date {
  font-size: 0.8rem;
  color: #64748b;
  margin: 0;
}

.video-stats {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 1rem;
}

.views {
  font-size: 0.9rem;
  color: #64748b;
}

.like-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  border-radius: 20px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.like-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
}

.heart-icon {
  width: 20px;
  height: 20px;
  fill: white;
  transition: transform 0.3s ease;
}

.heart-icon.liked {
  animation: likeAnimation 0.3s ease;
  fill: #ec4899;
}

@keyframes likeAnimation {
  0% { transform: scale(1); }
  50% { transform: scale(1.4); }
  100% { transform: scale(1); }
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .video-item {
    flex-direction: column;
    gap: 1rem;
  }

  .thumbnail-container {
    flex: 0 0 auto;
    width: 100%;
    height: 200px;
  }

  .video-info {
    padding: 0;
  }
}
</style>