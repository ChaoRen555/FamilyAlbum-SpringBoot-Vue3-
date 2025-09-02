<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" placeholder="Please enter album name" style="width: 240px; margin-right: 5px"></el-input>
      <el-input v-model="data.userName" placeholder="Please enter user name" v-if="data.user.role === 'ADMIN'" style="width: 240px"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'USER'">
      <el-button type="primary" plain @click="handleAdd">Create Album</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px;" v-if="data.user.role === 'ADMIN'">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="Album Cover">
          <template #default="scope">
            <el-image style="width: 40px; height: 40px; border-radius: 5px" v-if="scope.row.img" :src="scope.row.img" :preview-src-list="[scope.row.img]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="Album Name"></el-table-column>
        <el-table-column prop="userName" label="User Name"></el-table-column>
        <el-table-column prop="description" label="Album Description" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="Created At"></el-table-column>
        <el-table-column prop="roleRadio" label="Album Permission"></el-table-column>
        <el-table-column prop="statusRadio" label="Status">
          <template v-slot="scope">
            <el-tag v-if="scope.row.statusRadio === 'Normal'" type="success">{{ scope.row.statusRadio }}</el-tag>
            <el-tag v-if="scope.row.statusRadio === 'Violation'" type="danger">{{ scope.row.statusRadio }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-else style="margin: 10px 0">
      <el-row :gutter="20">
        <el-col :span="8" v-for="item in data.tableData">
          <div style="display: flex; grid-gap: 10px" class="card">
            <img :src="item.img" alt="" style="width: 200px; height: 200px; border-radius: 5px; cursor: pointer" @click="navTo('/manager/pictures?id=' + item.id)">
            <div style="flex: 1">
              <div style="font-size: 18px">{{ item.name }}</div>
              <div style="margin-top: 10px; color: #666666; text-align: justify; height: 100px; line-height: 20px" class="line5">{{ item.description }}</div>
              <div style="margin-top: 5px; color: #666666">Created At: {{ item.time }}</div>
              <div style="margin-top: 10px; display: flex; align-items: center; grid-gap: 10px">
                <el-tag v-if="item.roleRadio === 'Public'" size="large" type="success">{{ item.roleRadio }}</el-tag>
                <el-tag v-else type="danger" size="large">{{ item.roleRadio }}</el-tag>
                <el-tag v-if="item.statusRadio === 'Normal'" size="large" type="success">{{ item.statusRadio }}</el-tag>
                <el-tag v-else type="danger" size="large">{{ item.statusRadio }}</el-tag>
                <el-icon size="30" style="cursor: pointer; color: #4486ea" @click="handleEdit(item)"><Edit /></el-icon>
                <el-icon size="26" style="cursor: pointer; color: red" @click="del(item.id)"><Delete /></el-icon>
                <el-icon size="26" style="cursor: pointer; color: #30cbc5" @click="handleSubmit(item)" v-if="item.statusRadio === 'Violation'"><Position /></el-icon>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="Album Information" v-model="data.formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :rules="data.rules" :model="data.form" label-width="80px"  style="padding: 20px 30px" ref="formRef">
        <el-form-item label="Album Cover" prop="img" v-if="data.user.role === 'USER'">
          <el-upload
              :action="baseUrl + '/files/upload'"
              :headers="{ token: data.user.token }"
              list-type="picture"
              :on-success="handleImgSuccess"
          >
            <el-button type="primary">Upload</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="Album Name" prop="name" v-if="data.user.role === 'USER'">
          <el-input v-model="data.form.name" placeholder="Album Name"></el-input>
        </el-form-item>
        <el-form-item label="Album Description" prop="description" v-if="data.user.role === 'USER'">
          <el-input type="textarea" :rows="4" v-model="data.form.description" placeholder="Album Description"></el-input>
        </el-form-item>
        <el-form-item label="Album Permission" prop="roleRadio" v-if="data.user.role === 'USER'">
          <el-radio-group v-model="data.form.roleRadio">
            <el-radio-button label="Public" value="Public" />
            <el-radio-button label="Private" value="Private" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Album Status" prop="statusRadio" v-if="data.user.role === 'ADMIN'">
          <el-radio-group v-model="data.form.statusRadio">
            <el-radio-button label="Normal" value="Normal" />
            <el-radio-button label="Violation" value="Violation" />
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">Cancel</el-button>
          <el-button type="primary" @click="save">Save</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog title="Appeal Information" v-model="data.appealVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="80px"  style="padding: 20px 30px" ref="formRef">
        <el-form-item label="Appeal Content" prop="description">
          <el-input type="textarea" :rows="4" v-model="data.form.content" placeholder="Appeal Content"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.appeal = false">Cancel</el-button>
          <el-button type="primary" @click="appeal">Submit</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";
import router from "@/router/index.js";
const baseUrl = import.meta.env.VITE_BASE_URL
const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // current page number
  pageSize: 6,  // items per page
  formVisible: false,
  appealVisible: false,
  form: {},
  ids: [],
  name: null,
  userName: null,
  userList: [],
  rules: {
    img: [
      { required: true, message: 'Please upload an image', trigger: 'blur' },
    ],
    name: [
      { required: true, message: 'Please enter a name', trigger: 'blur' },
    ],
    description: [
      { required: true, message: 'Please enter a description', trigger: 'blur' },
    ],
  }
})

const formRef = ref()

const handleImgSuccess = (res) => {
  data.form.img = res.data
}

const navTo = (url) => {
  location.href = url
}

// load table data
const load = () => {
  request.get('/category/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      userName: data.userName
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

// open add dialog
const handleAdd = () => {
  data.form = {}
  data.form.status = 'Normal'
  data.form.userId = data.user.id
  data.form.roleRadio = 'Public'
  data.formVisible = true
}

// open edit dialog
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
// open appeal dialog
const handleSubmit = (row) => {
  data.form = {}
  data.form.userId = row.userId
  data.form.categoryId = row.id
  data.form.status = 'Pending Review'
  data.appealVisible = true
}

// add
const add = () => {
  request.post('/category/add', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      ElMessage.success('Operation successful')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// update
const update = () => {
  request.put('/category/update', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      ElMessage.success('Operation successful')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const appeal = () => {
  request.post('/appeal/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('Submitted successfully, waiting for admin review')
      data.appealVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// delete
const del = (id) => {
  ElMessageBox.confirm('Deleting the album will also delete its photos. This action cannot be undone. Are you sure?', 'Delete Confirmation', { type: 'warning' }).then(res => {
    request.delete('/category/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success('Deleted successfully')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const save = () => {
  formRef.value.validate(valid => {
    if (valid) {
      data.form.id ? update() : add()
    }
  })
}

const reset = () => {
  data.name = null
  data.userName = null
  load()
}

load()
</script>
