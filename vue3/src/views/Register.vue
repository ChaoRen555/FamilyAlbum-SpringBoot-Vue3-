<template>
  <div class="login-container">
    <div class="login-box">
      <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px; color: #1450aa">Welcome
        Dear Family
      </div>
      <el-form :model="data.form" ref="formRef" :rules="data.rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" size="large" v-model="data.form.username"
                    placeholder="Username"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" size="large" v-model="data.form.password"
                    placeholder="Password"
                    show-password/>
        </el-form-item>
        <el-form-item prop="role">
          <el-select size="large" v-model="data.form.role">
            <el-option value="USER" label="Users"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="large" type="primary" style="width: 100%" @click="register">Register</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: right;">
        <a href="/login">Login</a>
      </div>
    </div>

  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {User, Lock} from "@element-plus/icons-vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const data = reactive({
  form: {},
  roles: [],
  rules: {
    username: [
      {required: true, message: 'Please enter the username', trigger: 'blur'},
    ],
    password: [
      {required: true, message: 'Please enter the password', trigger: 'blur'},
    ],
    role: [
      {required: true, message: 'Please choose the role', trigger: 'blur'},
    ],
  }
})

const formRef = ref()


const register = () => {
  formRef.value.validate((valid => {
    if (valid) {

      request.post('/register', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success("Successfully registered!");
          setInterval(() => {
            location.href = '/login'
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
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to top, #ffe5b9, #fff8e7);
}

.login-box {
  width: 350px;
  padding: 50px 30px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, .1);
  background-color: rgba(255, 255, 255, .5);
}
</style>