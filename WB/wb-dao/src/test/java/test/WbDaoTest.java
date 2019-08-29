package test;

import dev.gavin.wb.dao.WbUserInfMapper;
import dev.gavin.wb.dao.ext.WbTypeDataMapper;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.model.WbUserInfExample;
import dev.gavin.wb.model.ext.WbTypeData;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class WbDaoTest{

    @Resource
    private WbUserInfMapper wbUserInfMapper;

    @Resource
    private WbTypeDataMapper wbTypeDataMapper;


   // @Test
    public void myTest(){

        String sql = "SELECT role_name AS NAME, description AS VALUE FROM wb_user_role";

        List<WbTypeData> list = wbTypeDataMapper.getTypeData(sql);

        for (WbTypeData data: list){
            System.out.println("*******");
            System.out.println(data.getName() + ":" + data.getValue());
        }


    }

    @Transactional
  //  @Test
    public void userDaoTest(){
        WbUserInf inf = new WbUserInf();
        inf.setGender("F");
        inf.setLoginName("test");
        inf.setLoginPassword("123456");
        inf.setMobile("13434578987");
        inf.setNickName("nick");
        inf.setStatus((byte)0);
        System.out.println(wbUserInfMapper.insert(inf));
        System.out.println(inf.getUserId());
    }

    @Test
    public void selectUserPage(){
        WbUserInfExample ex = new WbUserInfExample();
       // ex.createCriteria().andLoginNameEqualTo("test");
        List<WbUserInf> list = wbUserInfMapper.selectByExampleWithRowbounds(ex, new RowBounds(0,5));

        System.out.println("***** start ****");
        for(WbUserInf inf : list){

            System.out.println(inf);
        }
        System.out.println("***** end ****");

    }

}

