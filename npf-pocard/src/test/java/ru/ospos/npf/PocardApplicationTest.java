package ru.ospos.npf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.dto.Search;
import ru.ospos.npf.web.SearchController;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PocardApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PocardApplicationTest.class);

    @Autowired
    private PocardRepository pocardRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SearchController searchController;

    @Test
    public void test1() {

        Iterable<Pocard> all = pocardRepository.findAll();
        Assert.assertNotNull(all);
        LOGGER.info("TEST 1 PASSED.");
    }

    @Test
    public void test2() {

        List<Pocard> all = pocardRepository.findAll();
        Assert.assertEquals(0, all.size());
        LOGGER.info("TEST 2 PASSED.");
    }

    @Test
    public void test3() {

        DataResult<Search> searchDataResult = searchController.create(new Search());
        Assert.assertNotNull(searchDataResult);

        LOGGER.info("TEST 3 PASSED.");
    }
}
