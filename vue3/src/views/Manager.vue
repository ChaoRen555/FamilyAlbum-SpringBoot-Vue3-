<template>
  <div class="manager-container">
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="../assets/imgs/logo.png" alt="">
        <div class="title">FamilyAlbum</div>
      </div>
      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item to="/manager/home">Home</el-breadcrumb-item>
          <el-breadcrumb-item>{{ router.currentRoute.value.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="manager-header-right">
        <el-dropdown style="cursor: pointer;">
          <div style="padding-right: 20px; display: flex; align-items: center;">
            <img v-if="data.user.avatar" :src="data.user?.avatar" alt="" style="width: 40px; height: 40px; display: block; border-radius: 50%">
            <img v-else src="@/assets/imgs/ava.png" alt="" style="width: 40px; height: 40px; display: block; border-radius: 50%">
            <span style="margin-left: 5px">{{ data.user?.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="router.push('/manager/person')">Profile</el-dropdown-item>
              <el-dropdown-item @click.native="router.push('/manager/password')">Change Password</el-dropdown-item>
              <el-dropdown-item @click.native="logout">Log Out</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div style="display: flex">
      <div class="manager-main-left">
        <el-menu
            :default-active="router.currentRoute.value.path"
            :default-openeds="['1', '2']"
            router
        >
          <el-menu-item index="/manager/home">
            <el-icon><home-filled /></el-icon><span>System Home</span>
          </el-menu-item>
          <el-menu-item index="/manager/albums">
            <el-icon><Picture /></el-icon><span>Albums</span>
          </el-menu-item>

          <el-sub-menu index="2" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>Info</span>
            </template>
            <el-menu-item index="/manager/admin">Admin Info</el-menu-item>
            <el-menu-item index="/manager/user">User Info</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>

      <div class="manager-main-right">
        <router-view @updateUser="updateUser" />
      </div>
    </div>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";

const data = reactive({
  user:  JSON.parse(localStorage.getItem('xm-user') || '{}')
})

const logout = () => {
  localStorage.removeItem('xm-user')
  router.push('/login')
}

if (!data.user.id) {
  logout()
  ElMessage.error('Please log in!')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('xm-user') || '{}')
}
</script>

<style scoped>
@import '@/assets/css/manager.css';
</style>
