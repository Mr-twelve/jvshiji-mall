package com.shier.mall.dao;

import com.shier.mall.entity.Goods;
import com.shier.mall.entity.StockNumDTO;
import com.shier.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/18
 */
@Mapper
public interface GoodsMapper {

    int addGoods(Goods goods);

    /**
     * 根据Id查询商品
     * @param goodsIds
     * @return
     */
    List<Goods> selectByPrimaryKeys(List<Long> goodsIds);

    List<Goods> findGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalGoodsBySearch(PageQueryUtil pageUtil);

    Goods selectByPrimaryKey(Long goodsId);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    List<Goods> findGoodsList(PageQueryUtil pageUtil);;

    int getTotalGoods(PageQueryUtil pageUtil);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}
