<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #409EFF">
              <el-icon size="30"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">1,250</div>
              <div class="stat-label">会员总数</div>
            </div>
          </div>
          <div class="stat-footer">
            <span class="growth-rate positive">+12.5%</span>
            <span>较上月</span>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #67C23A">
              <el-icon size="30"><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">890</div>
              <div class="stat-label">客户总数</div>
            </div>
          </div>
          <div class="stat-footer">
            <span class="growth-rate positive">+8.3%</span>
            <span>较上月</span>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #E6A23C">
              <el-icon size="30"><ShoppingCart /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">456</div>
              <div class="stat-label">订单数量</div>
            </div>
          </div>
          <div class="stat-footer">
            <span class="growth-rate positive">+15.2%</span>
            <span>较上月</span>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #F56C6C">
              <el-icon size="30"><Money /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">258万</div>
              <div class="stat-label">销售额</div>
            </div>
          </div>
          <div class="stat-footer">
            <span class="growth-rate positive">+18.6%</span>
            <span>较上月</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>会员增长趋势</span>
          </template>
          <div ref="memberChart" style="height: 300px"></div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>销售额趋势</span>
          </template>
          <div ref="salesChart" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>客户转化漏斗</span>
          </template>
          <div ref="funnelChart" style="height: 300px"></div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>业绩排行榜</span>
          </template>
          <el-table :data="rankingData" style="width: 100%">
            <el-table-column type="index" label="排名" width="80" />
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="amount" label="销售额" />
            <el-table-column prop="count" label="订单数" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const memberChart = ref(null)
const salesChart = ref(null)
const funnelChart = ref(null)

const rankingData = ref([
  { name: '张三', amount: '¥125,000', count: 45 },
  { name: '李四', amount: '¥98,000', count: 38 },
  { name: '王五', amount: '¥87,000', count: 32 },
  { name: '赵六', amount: '¥76,000', count: 28 },
  { name: '钱七', amount: '¥65,000', count: 25 }
])

onMounted(() => {
  initMemberChart()
  initSalesChart()
  initFunnelChart()
})

const initMemberChart = () => {
  const chart = echarts.init(memberChart.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: { type: 'value' },
    series: [{
      data: [850, 920, 1010, 1100, 1180, 1250],
      type: 'line',
      smooth: true,
      areaStyle: {}
    }]
  })
}

const initSalesChart = () => {
  const chart = echarts.init(salesChart.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: { type: 'value' },
    series: [{
      data: [180, 195, 210, 225, 240, 258],
      type: 'bar',
      itemStyle: { color: '#409EFF' }
    }]
  })
}

const initFunnelChart = () => {
  const chart = echarts.init(funnelChart.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    series: [{
      type: 'funnel',
      data: [
        { value: 1000, name: '潜在客户' },
        { value: 600, name: '意向客户' },
        { value: 350, name: '商机客户' },
        { value: 180, name: '成交客户' }
      ]
    }]
  })
}
</script>

<style scoped>
.dashboard {
  width: 100%;
}

.stat-card {
  cursor: pointer;
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.stat-footer {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #EBEEF5;
  font-size: 12px;
  color: #909399;
}

.growth-rate {
  margin-right: 10px;
  font-weight: bold;
}

.growth-rate.positive {
  color: #67C23A;
}

.growth-rate.negative {
  color: #F56C6C;
}
</style>
