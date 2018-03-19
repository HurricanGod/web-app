package cn.hurrican.test;

import cn.hurrican.service.SearchService;
import cn.hurrican.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

//@ContextConfiguration(locations = {"classpath:spring-service.xml"})
@ContextConfiguration(classes = SearchServiceImpl.class)
public class SpringTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testMethod() {
        searchService.save();
    }

}
