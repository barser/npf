package ru.ospos.npf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.commons.util.OperationResult;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class PocardControllerApi {

    private final EntityManager entityManager;
    private final PocardRepository pocardRepository;

    public PocardControllerApi(@Autowired EntityManager entityManager, @Autowired PocardRepository pocardRepository) {
        this.entityManager = entityManager;
        this.pocardRepository = pocardRepository;
    }

    @GetMapping("/list")
    public DataResult<Pocard> list() {
        List<Pocard> pocards = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100000));
        //return DataResult.data(PocardDto.from(pocards));
        return DataResult.data(pocards);
    }




    @Transactional
    @GetMapping("/test")
    public OperationResult test(Authentication auth) {

//        Document document = entityManager.find(Document.class, 25191988);
//        TreeNode treeNode = entityManager.getReference(TreeNode.class, 3050);
//        document.addTreeNode(treeNode);
/*

        List<Pocard> first3ByAmountGreaterThanEqual = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100_000));
        Operator baranov = entityManager.find(Operator.class, 172);
        baranov.setLogin("BARANOV");
        entityManager.createQuery("select d from Document d order by d.id desc")
                .setMaxResults(100)
                .getResultList();
 */

        return OperationResult.success();
    }
}
