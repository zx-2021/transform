package acq.nrk.image.transform.service;

import acq.nrk.image.transform.entity.ResultJson;
import org.springframework.stereotype.Service;

@Service
public interface TransformService {

    public ResultJson getHeifByPng(String url);

}
