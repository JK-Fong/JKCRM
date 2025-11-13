<template>
  <div class="customer-list">
    <el-card>
      <div class="toolbar">
        <el-button type="primary">
          <el-icon><Plus /></el-icon>
          新增客户
        </el-button>
        <el-input
          v-model="searchKeyword"
          placeholder="搜索客户"
          clearable
          style="width: 300px"
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>
      
      <el-table :data="tableData" style="width: 100%; margin-top: 20px">
        <el-table-column prop="customerNo" label="客户编号" width="150" />
        <el-table-column prop="customerName" label="客户名称" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="industry" label="所属行业" />
        <el-table-column prop="level" label="客户级别" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag>{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default>
            <el-button link type="primary">编辑</el-button>
            <el-button link type="primary">跟进</el-button>
            <el-button link type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'

const searchKeyword = ref('')
const tableData = ref([
  {
    customerNo: 'C202401001',
    customerName: '阿里巴巴集团',
    phone: '0571-88158000',
    industry: '互联网',
    level: 'A级',
    status: 3
  }
])

const getStatusText = (status) => {
  const map = { 1: '潜在客户', 2: '意向客户', 3: '成交客户', 4: '流失客户' }
  return map[status] || ''
}
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
