<template>
  <div>
    <div class="card">Hello <span style="font-weight: bold">{{ data.user?.name }}</span>,   Welcome and Have a good day!</div>

    <div v-if="data.user.role === 'ADMIN'" class="card" style="margin-top: 10px; height: 600px" id="bar"></div>
  </div>
</template>

<script setup>
import {reactive} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  noticeList: [],
})

</script>

