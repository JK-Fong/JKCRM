<template>
  <div class="opportunity-list">
    <el-card>
      <el-button type="primary"><el-icon><Plus /></el-icon>新增机会</el-button>
      <el-table :data="tableData" style="width: 100%; margin-top: 20px">
        <el-table-column prop="opportunityName" label="机会名称" />
        <el-table-column prop="customerName" label="客户名称" />
        <el-table-column prop="amount" label="预计金额" />
        <el-table-column prop="stage" label="销售阶段" />
        <el-table-column prop="probability" label="成功概率" />
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
</script>
