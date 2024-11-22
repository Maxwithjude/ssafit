import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

export const useYoutubeStore = defineStore("youtube", () => {
  const videoList = ref([]);

  const youtubeSearch = function (keyword) {
    const url = `https://www.googleapis.com/youtube/v3/search`;
    const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

    axios({
      url,
      method: "GET",
      params: {
        key: API_KEY,
        part: "snippet",
        maxResults: 10,
        q: keyword,
        type: "video",
      },
    })
      .then((response) => {
        console.log(response.data);
        videoList.value = response.data.items;
      })
      .catch(() => {});
  };

  const popularVideoList = ref([]);
  const searchPopular = function (keyword) {
    const url = `https://www.googleapis.com/youtube/v3/search`;
    const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;
    
    const startTime = Date.now();
    const cacheKey = `youtube_popular_${keyword}`;
    const cacheData = localStorage.getItem(cacheKey);
    const now = Date.now();
  
    if (cacheData) {
      const parsedCache = JSON.parse(cacheData);
      if (now - parsedCache.timestamp < 24 * 60 * 60 * 1000) { // 1일 = 24시간
        console.log("캐시된 데이터 사용");
        popularVideoList.value = parsedCache.data;
        return;
      } else {
        console.log("캐시 만료, 데이터 삭제");
        localStorage.removeItem(cacheKey);
      }
    }
  
    axios({
      url,
      method: "GET",
      params: {
        key: API_KEY,
        part: "snippet",
        maxResults: 3,
        order: "viewCount",
        q: keyword,
        type: "video",
      },
    })
      .then((response) => {
        const endTime = Date.now();
        const elapsedTime = endTime - startTime;
        console.log("유튜브 API 요청 시간: ", elapsedTime, "ms");
  
        popularVideoList.value = response.data.items;
  
        const cacheObject = {
          data: response.data.items,
          timestamp: now,
        };
        localStorage.setItem(cacheKey, JSON.stringify(cacheObject));
  
        const savedCache = localStorage.getItem(cacheKey);
        if (savedCache) {
          console.log("로컬 스토리지에 데이터가 성공적으로 저장되었습니다.");
        } else {
          console.error("로컬 스토리지에 데이터 저장 실패");
        }
      })
      .catch((error) => {
        console.error("API 요청 실패: ", error);
      });
  };  

  const selectVideo = ref(null);

  const clickVideo = function (video) {
    selectVideo.value = video;
  };

  return {
    youtubeSearch,
    videoList,
    selectVideo,
    clickVideo,
    searchPopular,
    popularVideoList,
  };
});