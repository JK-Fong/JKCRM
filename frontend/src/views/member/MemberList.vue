<template>
  <div class="member-list">
    <el-card>
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增会员
        </el-button>
        <div class="search-box">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索会员姓名/手机号/会员编号"
            clearable
            style="width: 300px"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" @click="handleSearch" />
            </template>
          </el-input>
        </div>
      </div>
      
      <el-table
        :data="tableData"
        style="width: 100%; margin-top: 20px"
        v-loading="loading"
      >
        <el-table-column prop="memberNo" label="会员编号" width="180" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="levelName" label="会员等级" width="120">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.levelId)">{{ row.levelName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="points" label="积分" width="100" />
        <el-table-column prop="balance" label="余额" width="100">
          <template #default="{ row }">
            ¥{{ row.balance }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="registerTime" label="注册时间" width="180" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" @click="handlePoints(row)">积分</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 20px; justify-content: flex-end"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
    
    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker
            v-model="form.birthday"
            type="date"
            placeholder="请选择生日"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="会员等级">
          <el-select v-model="form.levelId" placeholder="请选择会员等级" style="width: 100%">
            <el-option label="普通会员" :value="1" />
            <el-option label="银卡会员" :value="2" />
            <el-option label="金卡会员" :value="3" />
            <el-option label="钻石会员" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="来源渠道">
          <el-input v-model="form.source" placeholder="请输入来源渠道" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus } from '@element-plus/icons-vue'

const loading = ref(false)
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const tableData = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('新增会员')
const formRef = ref(null)
const form = reactive({
  name: '',
  phone: '',
  email: '',
  gender: 1,
  birthday: '',
  levelId: 1,
  source: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ]
}

onMounted(() => {
  loadData()
})

const loadData = () => {
  loading.value = true
  // 模拟数据
  setTimeout(() => {
    tableData.value = [
      {
        id: 1,
        memberNo: 'M202401001',
        name: '张三',
        phone: '13800138001',
        email: 'zhangsan@example.com',
        levelId: 3,
        levelName: '金卡会员',
        points: 5680,
        balance: 1250.00,
        status: 1,
        registerTime: '2024-01-15 10:30:00'
      },
      {
        id: 2,
        memberNo: 'M202401002',
        name: '李四',
        phone: '13800138002',
        email: 'lisi@example.com',
        levelId: 2,
        levelName: '银卡会员',
        points: 2350,
        balance: 680.00,
        status: 1,
        registerTime: '2024-02-20 14:20:00'
      }
    ]
    total.value = 2
    loading.value = false
  }, 500)
}

const handleSearch = () => {
  currentPage.value = 1
  loadData()
}

const handleSizeChange = () => {
  loadData()
}

const handleCurrentChange = () => {
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增会员'
  Object.assign(form, {
    name: '',
    phone: '',
    email: '',
    gender: 1,
    birthday: '',
    levelId: 1,
    source: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑会员'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该会员吗？', '提示', {
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
    loadData()
  })
}

const handlePoints = (row) => {
  ElMessageBox.prompt('请输入积分数量（正数为增加，负数为扣减）', '积分操作', {
    inputPattern: /^-?\d+$/,
    inputErrorMessage: '请输入有效的数字'
  }).then(({ value }) => {
    ElMessage.success('积分操作成功')
    loadData()
  })
}

const handleSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
      loadData()
    }
  })
}

const getLevelType = (levelId) => {
  const types = { 1: '', 2: 'info', 3: 'warning', 4: 'danger' }
  return types[levelId] || ''
}
</script>

<style scoped>
.member-list {
  width: 100%;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
