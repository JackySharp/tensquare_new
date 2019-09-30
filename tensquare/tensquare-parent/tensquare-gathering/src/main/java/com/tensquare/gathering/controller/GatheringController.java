package com.tensquare.gathering.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    //增加活动
    @PostMapping
    public Result add(@RequestBody Gathering gathering) {
        try {
            gatheringService.add(gathering);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加文章失败");
        }
        return new Result(true,ErrorCode.OK,"添加文章成功");
    }

    //查询所有活动
    @GetMapping
    public Result findAll() {
        List<Gathering> gatherings = gatheringService.findAll();
        if (null != gatherings) {
            return new Result(true,ErrorCode.OK,"查询所有文章成功",gatherings);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有文章失败");
        }
    }

    //依据id查询活动
    @GetMapping("/{gatheringId}")
    public Result findById(@PathVariable String gatheringId) {
        Gathering gathering = gatheringService.findById(gatheringId);
        if (null != gathering) {
            return new Result(true,ErrorCode.OK,"文章查询成功",gathering);
        } else {
            return new Result(false, ErrorCode.ERROR, "文章查询失败");
        }
    }

    //依据id修改活动
    @PutMapping("/{gatheringId}")
    public Result update(@PathVariable String gatheringId, @RequestBody Gathering gathering) {
        try {
            gatheringService.update(gatheringId,gathering);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改文章失败");
        }
        return new Result(true,ErrorCode.OK,"修改文章成功");
    }

    //依据id删除活动
    @DeleteMapping("/{gatheringId}")
    public Result remove(@PathVariable String gatheringId) {
        try {
            gatheringService.remove(gatheringId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除招聘信息失败");
        }
        return new Result(true,ErrorCode.OK,"删除招聘信息成功");
    }

}
