import com.lagou.dao.ResumeDao;
import com.lagou.pojo.Resume;
import net.bytebuddy.TypeCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {
    @Autowired
    private ResumeDao resumeDao;
    @Test
    public void testFindById(){
        Optional<Resume> optional = resumeDao.findById(1);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testSave(){
        Resume resume = new Resume();
        resume.setName("路飞");
        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    @Test
    public void testDelete(){
        resumeDao.deleteById(4);
    }

    @Test
    public void testJpql(){
        Resume resume = resumeDao.testJpql(1);
        System.out.println(resume);
    }

    @Test
    public void testPredicate(){
        Specification<Resume> resumeSpecification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path name = root.get("name");
                Path address = root.get("address");
                Predicate namePredicate = criteriaBuilder.equal(name, "张三");
                Predicate addressPredicate = criteriaBuilder.like(address.as(String.class), "北%");
                Predicate and = criteriaBuilder.and(namePredicate, addressPredicate);

                return and;
            }
        };
        Optional<Resume> one = resumeDao.findOne(resumeSpecification);
        Resume resume = one.get();
        System.out.println(resume);
    }


    @Test
    public void testSort(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Resume> all = resumeDao.findAll(sort);
        for (Resume resume : all) {
            System.out.println(resume);
        }
    }

    @Test
    public void testPage(){
        Pageable pageable = new PageRequest(0, 2);
        Page<Resume> all = resumeDao.findAll(pageable);
        for (Resume resume : all) {
            System.out.println(resume);
        }
    }


}
