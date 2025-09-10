package com.album.controller;

import com.album.common.Result;
import com.album.entity.Collect;
import com.album.service.CollectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/add")
    public Result addCollect(@RequestBody Collect collect) {
        collectService.add(collect);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteCollect(@PathVariable Integer id) {
        collectService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Collect collect = collectService.selectById(id);
        return Result.success(collect);
    }

    @GetMapping("/selectAll")
    public Result selectAll(@RequestBody Collect collect) {
        List<Collect> list = collectService.selectAll(collect);
        return Result.success(list);
    }

    @PutMapping("/update")
    public Result updateCollect(@RequestBody Collect collect) {
        collectService.update(collect);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result pageQuery(Collect collect,
                            @RequestParam(defaultValue = "5") Integer pageSize,
                            @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Collect> list = collectService.pageQuery(collect, pageNum, pageSize);
        return Result.success(list);
    }


}
