package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.entity.vo.MessageInfoVo;
import org.hero.renche.service.IMessageInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Title: Controller
 * @Description: 开票信息管理
 */
@RestController
@RequestMapping("/renche/messageInfo")
@Slf4j
public class MessageInfoController {

    @Autowired
    private IMessageInfoService messageInfoService;

    /**
     * 分页列表查询消息提醒信息
     * @param pageNo
     * @param pageSize
     * @param messageInfo
     * @returnp'r
     */
    @ApiOperation(value = "获取消息提醒列表", notes = "获取消息提醒列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<MessageInfo>> list(MessageInfoVo messageInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<PageInfo<MessageInfo>> result = new Result<>();
        PageInfo<MessageInfo> pageList = messageInfoService.qryMessageListByRecive(pageNo,pageSize,messageInfo);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 查询消息未读提醒信息
     * @param pageNo
     * @param pageSize
     * @param sysId
     * @returnp'r
     */
    @ApiOperation(value = "查询消息未读提醒信息", notes = "查询消息未读提醒信息", produces = "application/json")
    @GetMapping(value = "/notReadlist")
    public Result<PageInfo<MessageInfo>> notReadlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(name = "sysId") String sysId) {
        Result<PageInfo<MessageInfo>> result = new Result<>();
        MessageInfoVo vo = new MessageInfoVo();
        vo.setReciveUser(sysId);
        vo.setMessageStatus("1");//未读
        PageInfo<MessageInfo> pageList = messageInfoService.qryMessageListByRecive(pageNo,pageSize,vo);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param messageInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加消息信息")
    public Result<MessageInfo> add(@RequestBody MessageInfo messageInfo) {
        Result<MessageInfo> result = new Result<>();
        messageInfo.setCreateTime(new Date());
        try {
            boolean ok = messageInfoService.save(messageInfo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     * @param messageInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<MessageInfo> eidt(@RequestBody MessageInfo messageInfo) {
        Result<MessageInfo> result = new Result<>();
        MessageInfo messageInfoEntity = messageInfoService.getById(messageInfo.getMessageId());
        if (messageInfoEntity == null) {
                result.error500("未找到对应实体");
        } else {
            boolean ok = messageInfoService.updateMessageRead(messageInfo);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除合同信息")
    @PostMapping(value = "/delete")
    public Result<MessageInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<MessageInfo> result = new Result<>();
        if (id == null || "".equals(id.trim())) {
            result.error500("参数不识别！");
        } else {
            List<String> ids = new ArrayList<>();
            ids.add(id);
            boolean ok = messageInfoService.deleteMessage(ids);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteBatch")
    public Result<MessageInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MessageInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.messageInfoService.deleteMessage(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 一键已读
     * @return
     */
    @PostMapping(value = "/batchEditRead")
    public Result<MessageInfo> batchEditRead(@RequestParam(name = "reciveUser") String reciveUser) {
        Result<MessageInfo> result = new Result<>();
        boolean ok = messageInfoService.updateAllReadStatus(reciveUser);
        // TODO 返回false说明什么？
        if (ok) {
            result.success("修改成功!");
        }

        return result;
    }

}
