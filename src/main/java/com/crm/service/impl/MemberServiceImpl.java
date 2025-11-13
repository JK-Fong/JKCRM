package com.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crm.common.BusinessException;
import com.crm.common.ResultCode;
import com.crm.dto.MemberDTO;
import com.crm.entity.Member;
import com.crm.mapper.MemberMapper;
import com.crm.service.MemberService;
import com.crm.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 会员Service实现类
 * 
 * @author CRM Team
 */
@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    
    @Override
    public Page<MemberVO> getMemberPage(Integer pageNum, Integer pageSize, String keyword) {
        Page<Member> page = new Page<>(pageNum, pageSize);
        
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.and(w -> w.like(Member::getName, keyword)
                    .or().like(Member::getPhone, keyword)
                    .or().like(Member::getMemberNo, keyword));
        }
        wrapper.orderByDesc(Member::getCreateTime);
        
        Page<Member> memberPage = this.page(page, wrapper);
        
        // 转换为VO
        Page<MemberVO> voPage = new Page<>(memberPage.getCurrent(), memberPage.getSize(), memberPage.getTotal());
        voPage.setRecords(memberPage.getRecords().stream().map(this::convertToVO).toList());
        
        return voPage;
    }
    
    @Override
    public MemberVO getMemberById(Long id) {
        Member member = this.getById(id);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        return convertToVO(member);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMember(MemberDTO memberDTO) {
        // 检查手机号是否已存在
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getPhone, memberDTO.getPhone());
        if (this.count(wrapper) > 0) {
            throw new BusinessException("手机号已存在");
        }
        
        Member member = new Member();
        BeanUtil.copyProperties(memberDTO, member);
        
        // 生成会员编号
        member.setMemberNo("M" + IdUtil.getSnowflakeNextIdStr());
        member.setRegisterTime(LocalDateTime.now());
        
        // 设置默认值
        if (member.getPoints() == null) {
            member.setPoints(0);
        }
        if (member.getGrowthValue() == null) {
            member.setGrowthValue(0);
        }
        if (member.getStatus() == null) {
            member.setStatus(1);
        }
        
        this.save(member);
        log.info("创建会员成功: {}", member.getMemberNo());
        
        return member.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMember(Long id, MemberDTO memberDTO) {
        Member member = this.getById(id);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // 检查手机号是否被其他会员使用
        if (!member.getPhone().equals(memberDTO.getPhone())) {
            LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Member::getPhone, memberDTO.getPhone());
            wrapper.ne(Member::getId, id);
            if (this.count(wrapper) > 0) {
                throw new BusinessException("手机号已被其他会员使用");
            }
        }
        
        BeanUtil.copyProperties(memberDTO, member, "id", "memberNo", "registerTime");
        this.updateById(member);
        
        log.info("更新会员成功: {}", member.getMemberNo());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(Long id) {
        Member member = this.getById(id);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        this.removeById(id);
        log.info("删除会员成功: {}", member.getMemberNo());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteMember(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
        log.info("批量删除会员成功，数量: {}", ids.length);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPoints(Long memberId, Integer points, String reason) {
        Member member = this.getById(memberId);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        member.setPoints(member.getPoints() + points);
        member.setGrowthValue(member.getGrowthValue() + points);
        this.updateById(member);
        
        // TODO: 记录积分变动日志
        
        log.info("会员 {} 增加积分: {}, 原因: {}", member.getMemberNo(), points, reason);
        
        // 检查是否需要升级
        upgradeMemberLevel(memberId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductPoints(Long memberId, Integer points, String reason) {
        Member member = this.getById(memberId);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (member.getPoints() < points) {
            throw new BusinessException("积分不足");
        }
        
        member.setPoints(member.getPoints() - points);
        this.updateById(member);
        
        // TODO: 记录积分变动日志
        
        log.info("会员 {} 扣减积分: {}, 原因: {}", member.getMemberNo(), points, reason);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upgradeMemberLevel(Long memberId) {
        Member member = this.getById(memberId);
        if (member == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // TODO: 根据成长值判断是否需要升级会员等级
        // 这里需要查询会员等级表，根据成长值判断应该升级到哪个等级
        
        log.info("检查会员 {} 等级升级", member.getMemberNo());
    }
    
    /**
     * 转换为VO
     */
    private MemberVO convertToVO(Member member) {
        MemberVO vo = new MemberVO();
        BeanUtil.copyProperties(member, vo);
        
        // 设置性别描述
        if (member.getGender() != null) {
            vo.setGenderDesc(member.getGender() == 0 ? "女" : member.getGender() == 1 ? "男" : "未知");
        }
        
        // 设置状态描述
        vo.setStatusDesc(member.getStatus() == 1 ? "正常" : "禁用");
        
        // TODO: 查询会员等级名称
        
        return vo;
    }
}
