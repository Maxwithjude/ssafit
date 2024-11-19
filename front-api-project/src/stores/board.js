import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/api-board/board`

export const useBoardStore = defineStore('board', () => {
  const boardList = ref([]) //게시글 목록을 스토어에서 관리하겠다~
  const getBoardList = function () {
    //세션에 토큰 정보 없으면 권한 없다고 알려주기
    const token = sessionStorage.getItem('access-token')

    if (!token) {
      alert('로그인 시 이용 가능합니다.')
      return
    }
    //토큰 있으면 요청 보내기
    axios
      .get(REST_API_URL, {
        headers: {
          'access-token': token,
        },
      })
      .then((response) => {
        // console.log(response.data)
        boardList.value = response.data
      })
      .catch((err) => {
        console.error(err)
        alert('게시글을 가져오는 중 문제가 발생했습니다.')
      })
    //catch 라던지... 등등 조금 더 상세하게 처리해주면 좋다~~
  }

  //게시글 등록
  const createBoard = function (board) {
    axios({
      url: REST_API_URL,
      method: 'POST',
      //JSON 형태로 바꿔주지 않았지만 application/json 알아서 이게 적용이 되었다.
      data: board,
    })
      .then(() => {
        console.log('완료')
        router.push({ name: 'boardList' })
      })
      .catch(() => {
        console.log('실패')
      })
  }
  const board = ref({})

  const getBoard = function (id) {
    axios.get(`${REST_API_URL}/${id}`).then((response) => {
      board.value = response.data
    })
  }

  const updateBoard = function () {
    axios.put(REST_API_URL, board.value).then(() => {
      router.push({ name: 'boardList' })
    })
  }

  const searchBoardList = function (searchCondition) {
    axios
      .get(REST_API_URL, {
        params: searchCondition,
      })
      .then((res) => {
        boardList.value = res.data
      })
  }

  return {
    boardList,
    getBoardList,
    createBoard,
    board,
    getBoard,
    updateBoard,
    searchBoardList,
  }
})
