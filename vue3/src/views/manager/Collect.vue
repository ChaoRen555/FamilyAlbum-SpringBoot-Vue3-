<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.categoryName" placeholder="Enter album name to search" style="width: 240px; margin-right: 5px"></el-input>
      <el-input v-model="data.userName" placeholder="Enter user name to search" v-if="data.user.role === 'ADMIN'" style="width: 240px"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px;">
      <el-table :data="data.tableData" stripe>
        <el-table-column prop="userName" label="User Name"></el-table-column>
        <el-table-column prop="categoryName" label="Album Name">
          <template v-slot="scope">
            <div style="cursor: pointer; color: #4486ea" @click="navTo('/manager/pictures?id=' + scope.row.categoryId)">{{ scope.row.categoryName }}</div>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination
          @current-change="load"
          background
          layout="total, prev, pager, next"
          :page-size="data.pageSize"
          v-model:current-page="data.pageNum"
          :total="data.total"/>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";
const baseUrl = import.meta.env.VITE_BASE_URL

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // current page number
  pageSize: 5,  // number of items per page
  formVisible: false,
  form: {},
  ids: [],
  categoryName: null,
  userName: null,
  userList: [],
  categoryList: [],
})

const navTo = (url) => {
  location.href = url
}

const loadUser = () => {
  request.get('/user/selectAll').then(res => {
    if (res.code === '200') {
      data.userList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadUser()

const loadCategory = () => {
  request.get('/category/selectAll').then(res => {
    if (res.code === '200') {
      data.categoryList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadCategory()

// Load table data
const load = () => {
  request.get('/collect/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      categoryName: data.categoryName,
      userName: data.userName,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

// Open add dialog
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// Open edit dialog
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// Add new
const add = () => {
  request.post('/collect/add', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      ElMessage.success('Operation successful')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// Update
const update = () => {
  request.put('/collect/update', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      ElMessage.success('Operation successful')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// Delete single record
const del = (id) => {
  ElMessageBox.confirm('This action cannot be undone. Are you sure you want to delete?', 'Delete Confirmation', { type: 'warning' })
      .then(res => {
        request.delete('/collect/delete/' + id).then(res => {
          if (res.code === '200') {
            ElMessage.success('Deleted successfully')
            load()
          } else {
            ElMessage.error(res.msg)
          }
        })
      })
      .catch(err => {
        console.error(err)
      })
}

// Batch delete
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("Please select data first")
    return
  }
  ElMessageBox.confirm('This action cannot be undone. Are you sure you want to delete?', 'Delete Confirmation', { type: 'warning' })
      .then(res => {
        request.delete('/collect/delete/batch', {data: data.ids}).then(res => {
          if (res.code === '200') {
            ElMessage.success('Operation successful')
            load()  // refresh table data
          } else {
            ElMessage.error(res.msg)
          }
        })
      })
      .catch(err => console.log(err))
}

const save = () => {
  data.form.id ? update() : add()
}

// Reset filters
const reset = () => {
  data.categoryName = null
  data.userName = null
  load()
}

load()
</script>
