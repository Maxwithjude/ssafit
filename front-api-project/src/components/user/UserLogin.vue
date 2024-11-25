<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>로그인</h2>
        <p>
          아이디와 비밀번호를 입력해 로그인해주세요.<br />
          아직 회원이 아니라면 <RouterLink :to="{ name: 'Signup' }" class="signup-link">회원가입</RouterLink>을 먼저 해주시기 바랍니다.
        </p>
      </div>

      <form @submit.prevent="login" class="login-form">
        <div class="form-group">
          <label for="id">아이디</label>
          <input 
            id="id"
            type="text" 
            v-model.trim="id" 
            placeholder="아이디를 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <input 
            id="password"
            type="password" 
            v-model.trim="password" 
            placeholder="비밀번호를 입력하세요"
            required
          />
        </div>

        <button type="submit" class="login-button">
          로그인
          <span class="button-arrow">→</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const store = useUserStore()

const id = ref('')
const password = ref('')

const login = async () => {
  try {
    await store.userLogin(id.value, password.value)
    router.push({ name: 'home' })
  } catch (error) {
    alert('로그인 실패: ' + error.message)
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #fff5f5 0%, #fff0f7 100%);
  padding: 2rem;
}

.login-card {
  background: white;
  border-radius: 20px;
  padding: 3rem;
  width: 100%;
  max-width: 450px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
}

.login-header {
  text-align: center;
  margin-bottom: 2.5rem;
}

.login-header h2 {
  color: #1e293b;
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
}

.login-header p {
  color: #64748b;
  font-size: 0.95rem;
  line-height: 1.6;
}

.signup-link {
  color: #f97316;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.signup-link:hover {
  color: #ec4899;
}

.login-form {
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

.login-button {
  background: linear-gradient(135deg, #f97316, #ec4899);
  color: white;
  padding: 1rem;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 1rem;
}

.login-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
}

.button-arrow {
  transition: transform 0.3s ease;
}

.login-button:hover .button-arrow {
  transform: translateX(5px);
}

@media (max-width: 640px) {
  .login-card {
    padding: 2rem;
  }

  .login-header h2 {
    font-size: 1.75rem;
  }

  .form-group input {
    padding: 0.7rem 1rem;
  }
}
</style>