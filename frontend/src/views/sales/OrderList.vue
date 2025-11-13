<template>
  <div class="order-list">
    <el-card>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" />
        <el-table-column prop="customerName" label="客户" />
        <el-table-column prop="amount" label="订单金额" />
        <el-table-column prop="actualAmount" label="实付金额" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag>{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <el-button link type="primary">查看</el-button>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const tableData = ref([])
const getStatusText = (status) => {
  const map = { 1: '待支付', 2: '已支付', 3: '已发货', 4: '已完成', 5: '已取消' }
  return map[status] || ''
}
</script>
