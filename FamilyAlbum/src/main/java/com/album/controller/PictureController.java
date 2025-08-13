package com.album.controller;

import com.album.common.Result;
import com.album.entity.Picture;
import com.album.service.PictureService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/add")
    public Result add(@RequestBody Picture picture) {
        log.info("Add picture{}", picture);
        pictureService.addPicture(picture);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        pictureService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        pictureService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Picture picture) {
        log.info("Update picture{}", picture);
        pictureService.updateById(picture);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Picture picture = pictureService.selectById(id);
        return Result.success(picture);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Picture picture) {
        List<Picture> pictures = pictureService.selectAll(picture);
        return Result.success(pictures);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Picture picture,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Picture> page = pictureService.selectPage(picture,pageNum,pageSize);
        return Result.success(page);
    }


}
