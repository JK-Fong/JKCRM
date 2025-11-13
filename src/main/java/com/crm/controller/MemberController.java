package com.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crm.common.Result;
import com.crm.dto.MemberDTO;
import com.crm.service.MemberService;
import com.crm.vo.MemberVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 会员管理Controller
 * 
 * @author CRM Team
 */
@Tag(name = "会员管理", description = "会员管理相关接口")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    
    @Operation(summary = "分页查询会员列表")
    @GetMapping("/page")
    public Result<Page<MemberVO>> getMemberPage(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword) {
        Page<MemberVO> page = memberService.getMemberPage(pageNum, pageSize, keyword);
        return Result.success(page);
    }
    
    @Operation(summary = "根据ID获取会员详情")
    @GetMapping("/{id}")
    public Result<MemberVO> getMemberById(@Parameter(description = "会员ID") @PathVariable Long id) {
        MemberVO memberVO = memberService.getMemberById(id);
        return Result.success(memberVO);
    }
    
    @Operation(summary = "创建会员")
    @PostMapping
    public Result<Long> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        Long id = memberService.createMember(memberDTO);
        return Result.success("创建成功", id);
    }
    
    @Operation(summary = "更新会员")
    @PutMapping("/{id}")
    public Result<Void> updateMember(
            @Parameter(description = "会员ID") @PathVariable Long id,
            @Valid @RequestBody MemberDTO memberDTO) {
        memberService.updateMember(id, memberDTO);
        return Result.success("更新成功", null);
    }
    
    @Operation(summary = "删除会员")
    @DeleteMapping("/{id}")
    public Result<Void> deleteMember(@Parameter(description = "会员ID") @PathVariable Long id) {
        memberService.deleteMember(id);
        return Result.success("删除成功", null);
    }
    
    @Operation(summary = "批量删除会员")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteMember(@RequestBody Long[] ids) {
        memberService.batchDeleteMember(ids);
        return Result.success("批量删除成功", null);
    }
    
    @Operation(summary = "增加积分")
    @PostMapping("/{id}/points/add")
    public Result<Void> addPoints(
            @Parameter(description = "会员ID") @PathVariable Long id,
            @Parameter(description = "积分数量") @RequestParam Integer points,
            @Parameter(description = "原因") @RequestParam String reason) {
        memberService.addPoints(id, points, reason);
        return Result.success("积分增加成功", null);
    }
    
    @Operation(summary = "扣减积分")
    @PostMapping("/{id}/points/deduct")
    public Result<Void> deductPoints(
            @Parameter(description = "会员ID") @PathVariable Long id,
            @Parameter(description = "积分数量") @RequestParam Integer points,
            @Parameter(description = "原因") @RequestParam String reason) {
        memberService.deductPoints(id, points, reason);
        return Result.success("积分扣减成功", null);
    }
}
