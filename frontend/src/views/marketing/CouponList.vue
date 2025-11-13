<template>
  <div class="coupon-list">
    <el-card>
      <el-button type="primary"><el-icon><Plus /></el-icon>新增优惠券</el-button>
      <el-table :data="tableData" style="width: 100%; margin-top: 20px">
        <el-table-column prop="couponName" label="优惠券名称" />
        <el-table-column prop="couponType" label="类型">
          <template #default="{ row }">
            {{ getTypeText(row.couponType) }}
          </template>
        </el-table-column>
        <el-table-column prop="discountValue" label="优惠金额/折扣" />
        <el-table-column prop="totalCount" label="发行总量" />
        <el-table-column prop="usedCount" label="已使用" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
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
const getTypeText = (type) => {
  const map = { 1: '满减券', 2: '折扣券', 3: '代金券' }
  return map[type] || ''
}
</script>
