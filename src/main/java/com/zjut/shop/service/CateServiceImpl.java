package com.zjut.shop.service;
import java.util.ArrayList;

import com.zjut.shop.query.CateParam;
import com.zjut.shop.vo.CateVO;
import com.zjut.shop.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CateServiceImpl implements CateService {

    private static final List<CateVO> cates = new CopyOnWriteArrayList<>();

    static {
        CateVO cateVO = new CateVO();
        cateVO.setId(1);
        cateVO.setName("大家电");
        cateVO.setPid(0);
        cateVO.setLevel(0);
        cateVO.setChildren(new ArrayList<CateVO>());

        cates.add(cateVO);

        // 子
        List<CateVO> son = new ArrayList<>();
        CateVO oneSon = new CateVO();
        son.add(oneSon);
        cateVO.setChildren(son);

        oneSon.setId(3);
        oneSon.setName("电视");
        oneSon.setPid(1);
        oneSon.setLevel(1);

        // 子 子
        List<CateVO> sonSon = new ArrayList<>();
        CateVO oneSonSon = new CateVO();
        CateVO twoSonSon = new CateVO();
        sonSon.add(oneSonSon);
        sonSon.add(twoSonSon);
        oneSon.setChildren(sonSon);

        oneSonSon.setId(6);
        oneSonSon.setName("曲面电视");
        oneSonSon.setPid(3);
        oneSonSon.setLevel(2);

        twoSonSon.setId(7);
        twoSonSon.setName("海信电视");
        twoSonSon.setPid(3);
        twoSonSon.setLevel(2);

    }

    @Override
    public PageResult<List<CateVO>> selectList(CateParam cateParam) {

        return new PageResult<>(cates.size(), cates);
    }

}
