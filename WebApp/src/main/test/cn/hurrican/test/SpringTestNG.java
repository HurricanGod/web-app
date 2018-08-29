package cn.hurrican.test;

import cn.hurrican.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

//@ContextConfiguration(locations = {"classpath:spring-service.xml"})
//@ContextConfiguration(classes = SearchServiceImpl.class)
public class SpringTestNG /*extends AbstractTestNGSpringContextTests */ {

    @Autowired
    private SearchService searchService;

    public void testMethod() {
        searchService.save();
    }

}
