// vite.config.js
import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import veauryVitePlugins from "veaury/vite/index.js";

export default defineConfig({
  plugins: [
    //replaces the vite() plugin
    veauryVitePlugins({
      type: "vue",
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    host: "0.0.0.0", // 모든 네트워크 인터페이스에서 접근 가능하도록 설정
    port: 5173, // 서버 포트 번호 (기본 3000에서 변경 가능)
  },
});
