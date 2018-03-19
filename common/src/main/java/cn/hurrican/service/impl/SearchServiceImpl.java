package cn.hurrican.service.impl;

import cn.hurrican.service.SearchService;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    public void save() {
        System.out.println("save record to db");
    }
}
