<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" placeholder="Please enter album name" style="width: 240px; margin-right: 5px"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-row :gutter="20">
        <el-col :span="8" v-for="item in data.tableData" style="margin-bottom: 20px">
          <div style="display: flex; grid-gap: 10px" class="card">
            <img :src="item.img" alt="" style="width: 200px; height: 200px; border-radius: 5px; cursor: pointer" @click="navTo('/manager/pictures?id=' + item.id)">
            <div style="flex: 1">
              <div style="font-size: 18px">{{ item.name }}</div>
              <div style="margin-top: 10px; display: flex; align-items: center; grid-gap: 10px">
                <img :src="item.userAvatar" alt="" style="width: 30px; height: 30px; border-radius: 50%">
                <div style="font-size: 15px">{{ item.userName }}</div>
              </div>
              <div style="margin-top: 10px; color: #666666; text-align: justify; height: 60px; line-height: 20px" class="line3">{{ item.description }}</div>
              <div style="margin-top: 5px; color: #666666">Created At: {{ item.time }}</div>
              <div style="margin-top: 10px; display: flex; align-items: center; grid-gap: 10px">
                <el-tag v-if="item.roleRadio === 'Public'" size="large" type="success">{{ item.roleRadio }}</el-tag>
                <el-tag v-else type="danger" size="large">{{ item.roleRadio }}</el-tag>
                <el-tag v-if="item.statusRadio === 'Normal'" size="large" type="success">{{ item.statusRadio }}</el-tag>
                <el-tag v-else type="danger" size="large">{{ item.statusRadio }}</el-tag>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";
import router from "@/router/index.js";
const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // current page number
  pageSize: 6,  // items per page
  name: null,
})

const navTo = (url) => {
  location.href = url
}

// load table data
const load = () => {
  request.get('/category/selectAlbumPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      statusRadio: 'Normal',
      roleRadio: 'Public'
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

const reset = () => {
  data.name = null
  load()
}

load()
</script>
