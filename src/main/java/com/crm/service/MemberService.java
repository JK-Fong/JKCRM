package com.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crm.dto.MemberDTO;
import com.crm.entity.Member;
import com.crm.vo.MemberVO;

/**
 * 会员Service接口
 * 
 * @author CRM Team
 */
public interface MemberService extends IService<Member> {
    
    /**
     * 分页查询会员列表
     */
    Page<MemberVO> getMemberPage(Integer pageNum, Integer pageSize, String keyword);
    
    /**
     * 根据ID获取会员详情
     */
    MemberVO getMemberById(Long id);
    
    /**
     * 创建会员
     */
    Long createMember(MemberDTO memberDTO);
    
    /**
     * 更新会员
     */
    void updateMember(Long id, MemberDTO memberDTO);
    
    /**
     * 删除会员
     */
    void deleteMember(Long id);
    
    /**
     * 批量删除会员
     */
    void batchDeleteMember(Long[] ids);
    
    /**
     * 增加积分
     */
    void addPoints(Long memberId, Integer points, String reason);
    
    /**
     * 扣减积分
     */
    void deductPoints(Long memberId, Integer points, String reason);
    
    /**
     * 升级会员等级
     */
    void upgradeMemberLevel(Long memberId);
}
