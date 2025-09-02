<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" placeholder="Please enter a keyword" style="width: 240px"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'USER'">
      <el-button type="primary" plain @click="handleAdd">Add</el-button>
      <el-button type="danger" plain @click="delBatch">Delete Selected</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px;">
      <el-table :data="data.tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="Photo Image">
          <template #default="scope">
            <el-image style="width: 40px; height: 40px; border-radius: 5px" v-if="scope.row.img" :src="scope.row.img" :preview-src-list="[scope.row.img]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="Photo Name"></el-table-column>
        <el-table-column prop="description" label="Photo Description" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="User Name"></el-table-column>
        <el-table-column prop="categoryName" label="Album Name"></el-table-column>
        <el-table-column prop="time" label="Upload Time"></el-table-column>
        <el-table-column prop="roleRadio" label="Photo Permission"></el-table-column>
        <el-table-column prop="statusRadio" label="Photo Status">
          <template v-slot="scope">
            <el-tag type="warning" v-if="scope.row.statusRadio === 'Pending Review'">{{ scope.row.statusRadio }}</el-tag>
            <el-tag type="success" v-if="scope.row.statusRadio === 'Approved'">{{ scope.row.statusRadio }}</el-tag>
            <el-tag type="danger" v-if="scope.row.statusRadio === 'Rejected'">{{ scope.row.statusRadio }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="Photo Information" v-model="data.formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px 30px" ref="formRef">
        <el-form-item label="Photo Image" prop="img" v-if="data.user.role === 'USER'">
          <el-upload
              :action="baseUrl + '/files/upload'"
              :headers="{ token: data.user.token }"
              list-type="picture"
              :on-success="handleImgSuccess"
          >
            <el-button type="primary">Upload</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="Photo Name" prop="name" v-if="data.user.role === 'USER'">
          <el-input v-model="data.form.name" placeholder="Photo Name"></el-input>
        </el-form-item>
        <el-form-item label="Photo Description" prop="description" v-if="data.user.role === 'USER'">
          <el-input type="textarea" :rows="3" v-model="data.form.description" placeholder="Photo Description"></el-input>
        </el-form-item>
        <el-form-item label="Select Album" prop="categoryId" v-if="data.user.role === 'USER'">
          <el-select style="width: 100%" v-model="data.form.categoryId">
            <el-option v-for="item in data.categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Photo Permission" prop="roleRadio" v-if="data.user.role === 'USER'">
          <el-radio-group v-model="data.form.roleRadio">
            <el-radio-button label="Public" value="Public" />
            <el-radio-button label="Private" value="Private" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Photo Status" prop="statusRadio" v-if="data.user.role === 'ADMIN'">
          <el-radio-group v-model="data.form.statusRadio">
            <el-radio-button label="Pending Review" value="Pending Review" />
            <el-radio-button label="Approved" value="Approved" />
            <el-radio-button label="Rejected" value="Rejected" />
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

  </div>
</template>

<script setup>
import { reactive, ref } from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";
const baseUrl = import.meta.env.VITE_BASE_URL
const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // current page
  pageSize: 5,  // items per page
  formVisible: false,
  form: {},
  ids: [],
  name: null,
  categoryList: [],
  rules: {
    img: [
      { required: true, message: 'Please upload an image', trigger: 'blur' },
    ],
    name: [
      { required: true, message: 'Please enter a name', trigger: 'blur' },
    ],
    categoryId: [
      { required: true, message: 'Please select an album', trigger: 'blur' },
    ],
  }
})

const formRef = ref()

const handleImgSuccess = (res) => {
  data.form.img = res.data
}

const loadCategory = () => {
  request.get('/category/selectAll', {
    params: {
      userId: data.user.id
    }
  }).then(res => {
    if (res.code === '200') {
      data.categoryList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadCategory()
// load table data
const load = () => {
  let userId = data.user.role !== 'ADMIN' ? data.user.id : null
  request.get('/picture/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      userId: userId
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

// open add dialog
const handleAdd = () => {
  data.form = {}
  data.form.userId = data.user.id
  data.form.statusRadio = 'Pending Review'
  data.form.roleRadio = 'Public'
  data.formVisible = true
}

// open edit dialog
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// add
const add = () => {
  request.post('/picture/add', data.form).then(res => {
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
  request.put('/picture/update', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      ElMessage.success('Operation successful')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// delete
const del = (id) => {
  ElMessageBox.confirm('Deleted data cannot be recovered, are you sure?', 'Delete Confirmation', { type: 'warning' }).then(res => {
    request.delete('/picture/delete/' + id).then(res => {
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

// batch delete
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("Please select data")
    return
  }
  ElMessageBox.confirm('Deleted data cannot be recovered, are you sure?', 'Delete Confirmation', { type: 'warning' }).then(res => {
    request.delete('/picture/delete/batch', {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('Operation successful')
        load()  // refresh table data
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => console.log(err))
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
  load()
}

load()

</script>
