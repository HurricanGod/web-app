package cn.hurrican.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/10/25
 * @Modified 9:34
 */
@Service
public class ProgrammerMessageServiceImpl extends AbstractJobMessageService {

    @Override
    public AbstractJobMessageService register() {
        return null;
    }

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
        return null;
    }
}
