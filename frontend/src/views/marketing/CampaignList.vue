<template>
  <div class="campaign-list">
    <el-card>
      <el-button type="primary"><el-icon><Plus /></el-icon>新增活动</el-button>
      <el-table :data="tableData" style="width: 100%; margin-top: 20px">
        <el-table-column prop="campaignName" label="活动名称" />
        <el-table-column prop="campaignType" label="活动类型" />
        <el-table-column prop="startTime" label="开始时间" />
        <el-table-column prop="endTime" label="结束时间" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag>{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <el-button link type="primary">编辑</el-button>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'

const tableData = ref([])
const getStatusText = (status) => {
  const map = { 1: '未开始', 2: '进行中', 3: '已结束' }
  return map[status] || ''
}
</script>
