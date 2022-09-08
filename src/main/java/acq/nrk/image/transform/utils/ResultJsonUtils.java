package acq.nrk.image.transform.utils;

import acq.nrk.image.transform.entity.ResultJson;

public class ResultJsonUtils {

    public static ResultJson success(Object data){
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(0);
        resultJson.setMsg("success");
        resultJson.setData(data);
        return resultJson;
    }

    public static ResultJson failMsg(int code, String msg, Object data){
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        resultJson.setData(data);
        return resultJson;
    }

}
