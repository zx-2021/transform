package acq.nrk.image.transform.collect;

import acq.nrk.image.transform.entity.ResultJson;
import acq.nrk.image.transform.utils.HeifToPng;
import acq.nrk.image.transform.utils.ResultJsonUtils;
import org.springframework.stereotype.Component;

@Component
public class TransformCollect {

    public ResultJson getPngByUrl(String url){

        HeifToPng heifToPng = new HeifToPng(url);
        if (heifToPng.getHeifByUrl()){
            if(!heifToPng.HeifToPng())
                return ResultJsonUtils.failMsg(-200, "图片转换格式异常", heifToPng);
        }else
            return ResultJsonUtils.failMsg(-201, "通过来链接获取图片出现错误", heifToPng);

        String imageBase64 = heifToPng.getImageBase64();

        if (imageBase64 == null) return ResultJsonUtils.failMsg(-202, "读取转换后的格式出现错误", heifToPng);

        imageBase64 = imageBase64.replaceAll("[\\t\\n\\r]", "");

        if (!heifToPng.deleteImage()){
            return ResultJsonUtils.failMsg(-203, "删除获取图片出现异常，未成功删除", heifToPng);
        }

        return ResultJsonUtils.success(imageBase64);
    }

}
