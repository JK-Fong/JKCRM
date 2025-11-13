import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '数据看板' }
      },
      {
        path: 'member',
        name: 'Member',
        component: () => import('@/views/member/MemberList.vue'),
        meta: { title: '会员管理' }
      },
      {
        path: 'customer',
        name: 'Customer',
        component: () => import('@/views/customer/CustomerList.vue'),
        meta: { title: '客户管理' }
      },
      {
        path: 'opportunity',
        name: 'Opportunity',
        component: () => import('@/views/sales/OpportunityList.vue'),
        meta: { title: '销售机会' }
      },
      {
        path: 'order',
        name: 'Order',
        component: () => import('@/views/sales/OrderList.vue'),
        meta: { title: '订单管理' }
      },
      {
        path: 'campaign',
        name: 'Campaign',
        component: () => import('@/views/marketing/CampaignList.vue'),
        meta: { title: '营销活动' }
      },
      {
        path: 'coupon',
        name: 'Coupon',
        component: () => import('@/views/marketing/CouponList.vue'),
        meta: { title: '优惠券管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
