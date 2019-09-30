package com.tensquare.split.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import com.tensquare.common.exception.MyExceptionHandler;
import com.tensquare.split.service.SplitService;
import com.tensquare.split.pojo.Split;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.index.GeoIndexed;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/split")
public class SplitController {

    @Autowired
    private SplitService splitService;

    //增加吐槽
    @PostMapping
    public Result add(@RequestBody Split split) {
        try {
            splitService.add(split);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加吐槽失败");
        }
        return new Result(true,ErrorCode.OK,"添加吐槽成功");
    }

    //查询所有吐槽
    @GetMapping
    public Result findAll() {
        List<Split> splits = splitService.findAll();
        if (null != splits) {
            return new Result(true,ErrorCode.OK,"查询所有吐槽成功",splits);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有吐槽失败");
        }
    }

    //依据id查询吐槽
    @GetMapping("/{splitId}")
    public Result findById(@PathVariable String splitId) {
        Split split = splitService.findById(splitId);
        if (null != split) {
            return new Result(true,ErrorCode.OK,"吐槽查询成功",split);
        } else {
            return new Result(false, ErrorCode.ERROR, "吐槽查询失败");
        }
    }

    //依据id修改吐槽
    @PutMapping("/{splitId}")
    public Result update(@PathVariable String splitId, @RequestBody Split split) {
        try {
            splitService.update(splitId,split);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改吐槽失败");
        }
        return new Result(true,ErrorCode.OK,"修改吐槽成功");
    }

    //依据id删除吐槽
    @DeleteMapping("/{splitId}")
    public Result remove(@PathVariable String splitId) {
        try {
            splitService.remove(splitId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除吐槽失败");
        }
        return new Result(true,ErrorCode.OK,"删除吐槽成功");
    }

    //点赞吐槽
    @PutMapping("/thumbup/{splitId}")
    public Result thumbup(@PathVariable String splitId) {
        try {
            splitService.thumbup(splitId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, e.getMessage());
        }
        return new Result(true,ErrorCode.OK,"吐槽点赞成功");
    }

    //依据上级id查询吐槽数据并分页
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        PageResult<Split> splits = splitService.findByParentid(parentid, page, size);
        return new Result(true,ErrorCode.OK,"依据上级id查询吐槽成功",splits);
    }

}
