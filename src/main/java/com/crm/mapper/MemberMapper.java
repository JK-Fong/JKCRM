package com.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crm.entity.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员Mapper接口
 * 
 * @author CRM Team
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
