package com.mhb.mosa.controller;

import com.mhb.mosa.service.MosaService;
import com.mhb.mosa.util.ResultBeanUtils;
import com.mhb.mosa.util.ServerLimitUtils;
import com.mhb.mosa.vo.ListMosaRoomVO;
import com.mhb.mosa.vo.ResultBean;
import com.mhb.mosa.vo.ServerLimitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @date: 2021/6/10 9:32
 */
@Slf4j
@RestController
@RequestMapping("/mosa")
public class MosaController {

    @Autowired
    private MosaService mosaService;

    @GetMapping("/room/list")
    public ServerLimitVO listRoom(String pageSize, String pageNum) {
        List<ListMosaRoomVO> list = null;
        long total = 0;
        try {
            list = mosaService.listRoomLimit(pageSize, pageNum);
            total = mosaService.countRoom();
        } catch (Exception e) {
            log.error("获取房间列表-异常：", e);
        }
        return ServerLimitUtils.build(list, total);
    }

    @RequestMapping("/room/create/{userName}")
    public ResultBean createRoom(@PathVariable String userName) {
        ResultBean result;
        try {
            boolean create = mosaService.createRoom(userName);
            if (create) {
                result = ResultBeanUtils.success();
            } else {
                result = ResultBeanUtils.failed();
            }
        } catch (Exception e) {
            result = ResultBeanUtils.exception();
            log.error("创建房间-异常：", e);
        }
        return result;
    }

    @RequestMapping("/room/join/{roomId}/{userName}")
    public ResultBean joinRoom(@PathVariable String roomId, @PathVariable String userName) {
        ResultBean result;
        try {
            boolean join = mosaService.joinRoom(roomId, userName);
            if (join) {
                result = ResultBeanUtils.success();
            } else {
                result = ResultBeanUtils.failed();
            }
        } catch (Exception e) {
            result = ResultBeanUtils.exception();
            log.error("加入房间-异常：", e);
        }
        return result;
    }

}
