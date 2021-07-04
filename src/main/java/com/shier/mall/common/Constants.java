package com.shier.mall.common;

/**
 * 常量配置
 *
 * @author shier
 * @date 2021/3/12
 */
public class Constants {
    // 上传文件保存路径，最后记得加双斜杠
    public final static String FILE_UPLOAD_PATH_WINDOW = "D:\\Work\\project\\idea\\jvshiji-mall\\src\\main\\resources\\static\\upload\\";
    public final static String FILE_UPLOAD_PATH_Linux = "//home//shiers//project//jvshijimall//project//myresource//upload//";
    public final static String FILE_UPLOAD_PATH = FILE_UPLOAD_PATH_Linux;
    public final static int INDEX_CAROUSEL_NUMBER = 5;//首页轮播图数量(可根据自身需求修改)

    public final static int INDEX_GOODS_HOT_NUMBER = 4;//首页热卖商品数量
    public final static int INDEX_GOODS_NEW_NUMBER = 5;//首页新品数量
    public final static int INDEX_GOODS_RECOMMOND_NUMBER = 10;//首页推荐商品数量

    public final static String MALL_USER_SESSION_KEY = "jvShiJiMallUser";//session中user的key

    public final static int GOODS_SEARCH_PAGE_LIMIT = 10;//搜索分页的默认条数(每页10条)

    public final static int SEARCH_CATEGORY_NUMBER = 8;//搜索页一级分类的最大数量

    public final static int SELL_STATUS_UP = 0;//商品上架状态
    public final static int SELL_STATUS_DOWN = 1;//商品下架状态

    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;//购物车中单个商品的最大购买数量(可根据自身需求修改)
    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 5;//购物车中商品的最大数量(可根据自身需求修改)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 3;//我的订单列表分页的默认条数(每页3条)

}
