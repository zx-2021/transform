package acq.nrk.image.transform.service.impl;

import acq.nrk.image.transform.collect.TransformCollect;
import acq.nrk.image.transform.entity.ResultJson;
import acq.nrk.image.transform.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransformServiceImpl implements TransformService {

    @Autowired
    TransformCollect collect;


    @Override
    public ResultJson getHeifByPng(String url) {
        return collect.getPngByUrl(url);
    }
}
