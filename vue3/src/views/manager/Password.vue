<template>
  <div style="width: 50%;">
    <div class="card">
      <el-form ref="formRef" :rules="data.rules" :model="data.user" label-width="180px" style="padding: 20px 30px">
        <el-form-item prop="password" label="Old Password">
          <el-input v-model="data.user.password" show-password/>
        </el-form-item>
        <el-form-item prop="newPassword" label="NewPassword">
          <el-input v-model="data.user.newPassword" show-password/>
        </el-form-item>
        <el-form-item prop="confirmPassword" label="Confirm New Password">
          <el-input v-model="data.user.confirmPassword" show-password/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updatePassword">Save</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue"
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please confirm the password'))
  } else if (value !== data.user.newPassword) {
    callback(new Error('Password confirmation does not match'))
  } else {
    callback()
  }
}

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  rules: {
    password: [
      {required: true, message: 'Please enter old password', trigger: 'blur'},
    ],
    newPassword: [
      {required: true, message: 'Please enter new password', trigger: 'blur'},
    ],
    confirmPassword: [
      {required: true, message: 'Please confirm new password', trigger: 'blur'},
      {validator: validatePassword, required: true, trigger: 'blur'},
    ],
  }
})

const formRef = ref()
const updatePassword = () => {
  formRef.value.validate((valid => {
    if (valid) {
      request.put('/updatePassword', data.user).then(res => {
        if (res.code === '200') {
          ElMessage.success('Operation successful')
          logout()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  }))
}

const logout = () => {
  localStorage.removeItem('xm-user')
  router.push('/login')
}
</script>
