package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    //添加标签
    @PostMapping
    public Result add(@RequestBody Label label) {
        try {
            labelService.add(label);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"添加标签失败");
        }
        return new Result(true,ErrorCode.OK,"添加标签成功");
    }

    //查询所有标签
    @GetMapping
    public Result findAll(@RequestBody(required = false) Label label) {
        List<Label> labelList = labelService.findAll(label);
        if (null != labelList) {
            return new Result(true,ErrorCode.OK,"查询标签成功",labelList);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询标签失败");
        }
    }

    //查询推荐标签列表
    @GetMapping("/toplist")
    public Result findRecommendLabel() {
        List<Label> recommends = labelService.findRecommand();
        if (null != recommends) {
            return new Result(true,ErrorCode.OK,"查询推荐标签成功",recommends);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询推荐标签失败");
        }
    }

    //查询有效标签列表
    @GetMapping("/list")
    public Result findValidLabel() {
        List<Label> valids = labelService.findValid();
        if (null != valids) {
            return new Result(true,ErrorCode.OK,"查询有效标签成功",valids);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询有效标签失败");
        }
    }

    //查询标签
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable String labelId) {
        Label label = labelService.findById(labelId);
        if (null != label) {
            return new Result(true,ErrorCode.OK,"查询查询标签成功",label);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询标签失败");
        }
    }

    //修改标签
    @PutMapping("/{labelId}")
    public Result update(@PathVariable String labelId, @RequestBody Label label) {
        try {
            labelService.update(labelId,label);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"更新标签失败");
        }
        return new Result(true,ErrorCode.OK,"更新标签成功");
    }

    //删除标签
    @DeleteMapping("/{labelId}")
    public Result remove(@PathVariable String labelId) {
        try {
            labelService.remove(labelId);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"删除标签失败");
        }
        return new Result(true,ErrorCode.OK,"删除标签成功");
    }

    //按条件查询标签
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Label label) {
        List<Label> labelList = labelService.search(label);
        if (null != labelList) {
            return new Result(true,ErrorCode.OK,"查询成功",labelList);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询失败");
        }
    }

    //标签查询分页
    @PostMapping("/search/{page}/{size}")
    public Result findByPage(@PathVariable int page, @PathVariable int size, @RequestBody(required = false) Label label) {
        PageResult<Label> pageResult = labelService.findByPage(page, size, label);
        if (null != pageResult) {
            return new Result(true,ErrorCode.OK,"查询成功",pageResult);
        } else {
            return new Result(false,ErrorCode.ERROR,"查询失败");
        }
    }
}

