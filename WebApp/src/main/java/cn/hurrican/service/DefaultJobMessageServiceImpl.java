package cn.hurrican.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/10/25
 * @Modified 11:04
 */
@Service
public class DefaultJobMessageServiceImpl extends AbstractJobMessageService {
    /**
     * 分页查询职位信息
     *
     * @param start 分页查询参数，起始位置
     * @param size  分页查询参数，数据条数
     * @param type  类型
     * @return
     */
    @Override
    public List<Map<String, Object>> getJobInfoByPage(Integer start, Integer size, Integer type) {
        if(type == 0){
            return null;
        }else{

            return null;
        }
    }

    @Override
    public AbstractJobMessageService register() {

        return this;
    }
}
