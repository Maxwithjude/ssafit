<template>
  <v-app>
    <TheHeaderNav />
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import TheHeaderNav from './components/common/TheHeaderNav.vue';

export default {
  name: "App",
  components: {
    TheHeaderNav,
  },
};
</script>
