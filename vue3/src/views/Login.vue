<template>
  <div class="login-container">
    <div class="login-box">
      <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px; color: #1450aa">FamilyAlbum</div>
      <el-form :model="data.form"  ref="formRef" :rules="data.rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" size="large" v-model="data.form.username" placeholder="username" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" size="large" v-model="data.form.password" placeholder="password" show-password />
        </el-form-item>
        <el-form-item prop="role">
          <el-select v-model="data.form.role" placeholder="Role" size="large">
            <el-option value="ADMIN" label="Admin"></el-option>
            <el-option value="USER" label="User"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="large" type="primary" style="width: 100%" @click="login">Log</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: right;">
        New account? <a href="/register">Registration</a>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { reactive, ref } from "vue";
  import { User, Lock } from "@element-plus/icons-vue";
  import request from "@/utils/request";
  import {ElMessage} from "element-plus";

  const data = reactive({
    dialogVisible: true,
    form: {},
    rules: {
      username: [
        { required: true, message: 'Please enter your username', trigger: 'blur' },
      ],
      password: [
        { required: true, message: 'Please enter your password', trigger: 'blur' },
      ],
      role: [
        { required: true, message: 'Select role', trigger: 'blur' },
      ],
    }
  })

  const formRef = ref()


  const login = () => {
    formRef.value.validate((valid => {
      if (valid) {
        // send request
        request.post('/login', data.form).then(res => {
          if (res.code === '200') {
            ElMessage.success("Successfully logged in!");
            // Store data at local
            localStorage.setItem('xm-user', JSON.stringify(res.data))
            setTimeout(() => {
              location.href = '/manager/home'
            }, 500)
          } else {
            ElMessage.error(res.msg)
          }
        })
      }
    })).catch(error => {
      console.error(error)
    })
  }

</script>

<style scoped>
.login-container {
  height: 100vh;
  overflow:hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  //background: linear-gradient(to top, #7f7fd5, #86a8e7, #91eae4);
  background-image: url("@/assets/imgs/bg.jpg");
  background-size: cover;
}
.login-box {
  width: 350px;
  padding: 30px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0,.1);
  background-color: rgba(255, 255, 255, .5);
}
</style>