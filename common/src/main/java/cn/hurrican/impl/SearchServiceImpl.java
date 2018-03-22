package cn.hurrican.impl;

import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    public void save() {
        System.out.println("save record to db");
    }
}
