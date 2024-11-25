import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const REST_API_URL = "http://localhost:8080/api";

export const useYoutubeStore = defineStore("youtube", () => {
  const videoList = ref([]);

  // 유튜브 검색 기능
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

    // 캐시 데이터를 확인하고 사용
    if (cacheData) {
      const parsedCache = JSON.parse(cacheData);
      if (now - parsedCache.timestamp < 24 * 60 * 60 * 1000) {
        console.log("캐시된 데이터 사용");
        popularVideoList.value = parsedCache.data;
        return;
      } else {
        console.log("캐시 만료, 데이터 삭제");
        localStorage.removeItem(cacheKey);
      }
    }

    // API 호출 후 캐시 저장
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

  // 비디오 선택 기능
  const clickVideo = function (video) {
    selectVideo.value = video;
  };

  // **추가된 리뷰 목록 가져오기 기능**
  const fetchReviews = async (videoId) => {
    try {
      const response = await axios.get(`${REST_API_URL}/videos/${videoId}/reviews`);
      return response.data;
    } catch (error) {
      console.error("리뷰 목록 가져오기 실패:", error);
      throw new Error("리뷰 데이터를 불러오지 못했습니다.");
    }
  };

  // **추가된 리뷰 등록 기능**
  const addReview = async (videoId, review) => {
    try {
      await axios.post(`${REST_API_URL}/videos/${videoId}/reviews`, review);
    } catch (error) {
      console.error("리뷰 등록 실패:", error);
      throw new Error("리뷰 등록에 실패했습니다.");
    }
  };

  // **추가된 비디오 저장 기능**
  const saveVideo = async (video) => {
    try {
      await axios.post(`${REST_API_URL}/videos`, [video]);
      console.log("비디오 정보가 성공적으로 저장되었습니다.");
    } catch (error) {
      console.error("비디오 저장 실패:", error);
      throw new Error("비디오 정보를 저장하지 못했습니다.");
    }
  };

  // **추가된 비디오 목록 가져오기 기능**
  const fetchVideos = async () => {
    try {
      const response = await axios.get(`${REST_API_URL}/videos`);
      videoList.value = response.data;
    } catch (error) {
      console.error("비디오 목록 가져오기 실패:", error);
    }
  };

  return {
    youtubeSearch, // 유튜브 검색
    videoList, // 비디오 목록
    selectVideo, // 선택된 비디오
    clickVideo, // 비디오 선택
    searchPopular, // 인기 비디오 검색
    popularVideoList, // 인기 비디오 목록
    fetchReviews, // 리뷰 목록 가져오기 (추가된 부분)
    addReview, // 리뷰 등록 (추가된 부분)
    saveVideo, // 비디오 저장 (추가된 부분)
    fetchVideos, // 비디오 목록 가져오기 (추가된 부분)
  };
});
