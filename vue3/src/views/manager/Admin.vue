<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" prefix-icon="Search" style="width: 240px; margin-right: 10px"
                placeholder="Enter the Name"></el-input>
      <el-button type="info" plain @click="load">Search</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" plain @click="handleAdd">Add</el-button>
      <el-button type="danger" plain @click="delBatch"> Bulk Delete</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="username" label="Account"/>
        <el-table-column prop="name" label="Name"/>
        <el-table-column prop="avatar" label="Avatar">
          <template v-slot="scope">
            <el-image style="width: 40px; height: 40px; border-radius: 50%; display: block" v-if="scope.row.avatar"
                      :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="Phone"/>
        <el-table-column prop="email" label="Email"/>
        <el-table-column prop="role" label="Role"/>
        <el-table-column label="Actions" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize"
                     v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog v-model="data.formVisible" title="Admin Information" width="40%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" label-width="70px" style="padding: 20px">
        <el-form-item prop="username" label="Username">
          <el-input v-model="data.form.username" placeholder="Please enter the username"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="Name">
          <el-input v-model="data.form.name" placeholder="Please enter the name"/>
        </el-form-item>
        <el-form-item prop="phone" label="Phone">
          <el-input v-model="data.form.phone" placeholder="Please enter the phone number"/>
        </el-form-item>
        <el-form-item prop="email" label="Email">
          <el-input v-model="data.form.email" placeholder="Please enter the email"/>
        </el-form-item>
        <el-form-item prop="avatar" label="Avatar">
          <el-upload
              :action="baseUrl + '/files/upload'"
              :on-success="handleFileUpload"
              list-type="picture"
          >
            <el-button type="primary">Upload Avatar</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="data.formVisible = false">Cancel</el-button>
      <el-button type="primary" @click="save">Save</el-button>
    </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import {reactive} from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";

const baseUrl = import.meta.env.VITE_BASE_URL

const data = reactive({
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 5,
  formVisible: false,
  form: {},
  ids: [],
  name: null,
})

// 加载表格数据
const load = () => {
  request.get('/admin/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

// Open the dialog
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// The same dialog
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}


const add = () => {
  request.post('/admin/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('success')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}


const update = () => {
  request.put('/admin/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('success')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}


const del = (id) => {
  ElMessageBox.confirm('Are you sure you want to delete it?', 'Confirm', {type: 'warning'}).then(res => {
    request.delete('/admin/deleteById/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success('Deleted')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

// select ids
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}


const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("Please select")
    return
  }
  ElMessageBox.confirm('Are you sure you want to delete it?', 'confirm', {type: 'warning'}).then(res => {
    request.delete('/admin/delete/batch', {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('Deleted')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => console.log(err))
}


const save = () => {
  data.form.id ? update() : add()
}

// Upload avatar
const handleFileUpload = (res) => {
  data.form.avatar = res.data
}

const reset = () => {
  data.name = ''
  load()
}

load()

</script>