<template>
  <div class="signup-container">
    <div class="signup-card">
      <div class="signup-header">
        <h2>회원가입</h2>
        <p>필수 정보를 입력하고 회원가입을 완료하세요.</p>
      </div>
      
      <form @submit.prevent="handleSignup" class="signup-form">
        <div class="form-group">
          <label for="id">아이디</label>
          <input 
            id="id" 
            v-model="form.id" 
            type="text"
            placeholder="아이디를 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <input 
            id="password" 
            v-model="form.password" 
            type="password"
            placeholder="비밀번호를 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="name">이름</label>
          <input 
            id="name" 
            v-model="form.name" 
            type="text"
            placeholder="이름을 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input 
            id="nickname" 
            v-model="form.nickname" 
            type="text"
            placeholder="닉네임을 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="email">이메일</label>
          <input 
            id="email" 
            v-model="form.email" 
            type="email"
            placeholder="이메일을 입력하세요"
            required
          />
        </div>

        <button type="submit" class="signup-button">
          회원가입
          <span class="button-arrow">→</span>
        </button>

        <p class="login-link">
          이미 계정이 있으신가요?
          <RouterLink :to="{ name: 'login' }">로그인하기</RouterLink>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()

const form = reactive({
  id: '',
  password: '',
  name: '',
  nickname: '',
  email: '',
})

const handleSignup = async () => {
  try {
    await userStore.signup(form)
    router.push({ name: 'login' })
  } catch (error) {
    alert('회원가입에 실패했습니다. 다시 시도해주세요.')
  }
}
</script>

<style scoped>
.signup-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #fff5f5 0%, #fff0f7 100%);
  padding: 2rem;
}

.signup-card {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
}

.signup-header {
  text-align: center;
  margin-bottom: 2rem;
}

.signup-header h2 {
  color: #1e293b;
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.signup-header p {
  color: #64748b;
  font-size: 0.95rem;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  color: #1e293b;
  font-weight: 500;
  font-size: 0.95rem;
}

.form-group input {
  padding: 0.75rem 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.1);
}

.form-group input::placeholder {
  color: #94a3b8;
}

.signup-button {
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  padding: 1rem;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: opacity 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 1rem;
}

.signup-button:hover {
  opacity: 0.9;
}

.button-arrow {
  transition: transform 0.3s ease;
}

.signup-button:hover .button-arrow {
  transform: translateX(5px);
}

.login-link {
  text-align: center;
  margin-top: 1.5rem;
  color: #64748b;
  font-size: 0.95rem;
}

.login-link a {
  color: #f97316;
  text-decoration: none;
  font-weight: 500;
  margin-left: 0.5rem;
  transition: color 0.3s ease;
}

.login-link a:hover {
  color: #ec4899;
}

@media (max-width: 640px) {
  .signup-card {
    padding: 2rem;
  }

  .signup-header h2 {
    font-size: 1.75rem;
  }

  .form-group input {
    padding: 0.7rem 1rem;
  }
}
</style>