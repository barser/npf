package ru.ospos.npf.commons.domain.document;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ospos.npf.commons.domain.base.TreeNode;

import static org.slf4j.LoggerFactory.getLogger;

public class DocumentTest {

    private static final Logger LOGGER = getLogger(DocumentTest.class);

    @Test
    public void test0() {

        var document = new Document();
        var treeNode = new TreeNode();
        document.addTreeNode(treeNode);

        Assert.assertTrue(document.getTreeNodes().contains(treeNode));

        LOGGER.info("TEST 0 - DOMAIN MODEL - PASSED.");
    }

}
