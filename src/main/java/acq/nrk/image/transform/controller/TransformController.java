package acq.nrk.image.transform.controller;

import acq.nrk.image.transform.entity.ResultJson;
import acq.nrk.image.transform.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@RequestMapping("/transform")
public class TransformController {

    @Autowired
    TransformService transformService;

    @GetMapping("heif_to_png")
    public ResultJson searchKeyWord(String url) {
        return transformService.getHeifByPng(url);
    }

}
