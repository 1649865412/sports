package api.taobao.constant;

/**
 * 订单常量表 <code>TradeConstants.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class TradeConstants
{
	/**
	 * 卖家包邮
	 */
	public final static String FREE = "卖家包邮";
	
	/**
	 * 平邮
	 */
	public final static String POST = "平邮";
	/**
	 * 快递
	 */
	public final static String EXPRESS = "快递";
	/**
	 * EMS
	 */
	public final static String EMS = "EMS";
	
	/**
	 * 系统定义订单各种状态 : 没有创建支付宝交易
	 */
	public final static String TRADE_NO_CREATE_PAY = "没有创建支付宝交易";
	/**
	 * 系统定义订单各种状态 :等待买家付款
	 */
	public final static String WAIT_BUYER_PAY = "等待买家付款";
	/**
	 * 系统定义订单各种状态 : // 即:买家已付款
	 */
	public final static String WAIT_SELLER_SEND_GOODS = "等待卖家发货";
	/**
	 * 系统定义订单各种状态 : // 即:卖家已发货
	 */
	public final static String WAIT_BUYER_CONFIRM_GOODS = "等待买家确认收货";
	/**
	 * 系统定义订单各种状态 : 买家已签收,货到付款专用
	 */
	public final static String TRADE_BUYER_SIGNED = "买家已签收,货到付款专用";
	/**
	 * 系统定义订单各种状态 : 没有创建支付宝交易
	 */
	public final static String TRADE_FINISHED = "交易成功";
	/**
	 * 系统定义订单各种状态 : 交易关闭
	 */
	public final static String TRADE_CLOSED = "交易关闭";
	/**
	 * 系统定义订单各种状态 : 交易被淘宝关闭
	 */
	public final static String TRADE_CLOSED_BY_TAOBAO = "交易被淘宝关闭";
	/**
	 * 系统定义订单各种状态 : 交易被淘宝关闭 包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY
	 */
	public final static String ALL_WAIT_PAY = "交易被淘宝关闭"; // 包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY
	/**
	 * 系统定义订单各种状态 :交易被淘宝关闭 // 包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO
	 */
	public final static String ALL_CLOSED = "交易被淘宝关闭"; // 包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO
	/**
	 * 系统定义订单各种状态 : 等待京东确认收货
	 */
	public final static String WAIT_JINGDONG_CONFIRM_GOODS = "等待京东确认收货";
	
	// 淘宝定义订单各种状态
	/**
	 * 淘宝定义订单各种状态:没有创建支付宝交易
	 */
	public final static String STATUS_TRADE_NO_CREATE_PAY = "TRADE_NO_CREATE_PAY";// 没有创建支付宝交易
	
	/**
	 * 淘宝定义订单各种状态:等待买家付款
	 */
	public final static String STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; // 等待买家付款
	
	/**
	 * 淘宝定义订单各种状态: 交易成功
	 */
	public final static String STATUS_TRADE_FINISHED = "TRADE_FINISHED"; // 交易成功
	
	/**
	 * 淘宝定义订单各种状态:付款以后用户退款成功，交易自动关闭
	 */
	public final static String STATUS_TRADE_CLOSED = "TRADE_CLOSED"; // 付款以后用户退款成功，交易自动关闭
	
	/**
	 * 淘宝定义订单各种状态:交易已经关闭
	 */
	public final static String STATUS_ALL_CLOSED = "ALL_CLOSED";
	/**
	 * 淘宝定义订单各种状态:等待卖家发货,即:买家已付款
	 */
	public final static String STATUS_WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";// 等待卖家发货,即:买家已付款
	
	/**
	 * 淘宝定义订单各种状态:等待买家确认收货,即:卖家已发货
	 */
	public final static String STATUS_WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";// 等待买家确认收货,即:卖家已发货
	
	/**
	 * 淘宝定义订单各种状态:买家已经签收
	 */
	public final static String STATUS_BUYER_SIGNED = "BUYER_SIGNED"; // 买家已经签收
	
	/**
	 * 淘宝定义订单各种状态:订单在taobao上关闭,拍下没付款
	 */
	public final static String STATUS_TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO";// 订单在taobao上关闭,拍下没付款
	
	/**
	 * 淘宝定义订单各种状态:等待付款
	 */
	public final static String STATUS_ALL_WAIT_PAY = "ALL_WAIT_PAY"; // 等待付款
	
	/**
	 * 淘宝定义订单各种状态:定金未付尾款未付
	 */
	public final static String STEP_FRONT_NOPAID_FINAL_NOPAID = "FRONT_NOPAID_FINAL_NOPAID";// 定金未付尾款未付
	
	/**
	 * 淘宝定义订单各种状态:没有创建支付宝交易
	 */
	public final static String STEP_FRONT_PAID_FINAL_NOPAID = "FRONT_PAID_FINAL_NOPAID"; // 定金已付尾款未付
	
	/**
	 * 淘宝定义订单各种状态:没有创建支付宝交易
	 */
	public final static String STEP_FRONT_PAID_FINAL_PAID = "FRONT_PAID_FINAL_PAID"; // 定金和尾款都付
	
	/**
	 * 淘宝定义订单各种状态:京东等待出库LBP
	 */
	public final static String STATUS_WAIT_SELLER_STOCK_OUT = "WAIT_SELLER_STOCK_OUT"; // 京东等待出库LBP
	
	/**
	 * 淘宝定义订单各种状态: 京东等待出库SOP
	 */
	public final static String STATUS_WAIT_SELLER_DELIVERY = "WAIT_SELLER_DELIVERY"; // 京东等待出库SOP
	
	// 系统定义订单来源
	/**
	 * 系统定义订单来源：淘宝
	 */
	public static final int SOURCE_TAOBAO = 1; // 来源：淘宝
	
	/**
	 * 系统定义订单来源：系统订单
	 */
	public static final int SOURCE_locat = 9; // 来源：系统订单
	
	/**
	 * 系统定义订单来源：来源京东
	 */
	public static final int SOURCE_JINGDONG = 3; // 来源京东
	
	/**
	 * 系统定义订单来源：淘宝分销
	 */
	public static final int SOURCE_FENXIAO = 4; // 来源：淘宝分销
	
	/**
	 * 系统定义订单来源：渠道
	 */
	public static final int SOURCE_QIDAO = 5; // 来源：渠道
	
	// local_status 本地状态（-1 未下载处理完，暂不能在后台显示；0-未审核 1-已审核 2-挂起中 9-被合单）
	public static final int LOCAL_STATUS_INVAILD = 999; // 订单下载下来后，如果所有数据未保存好，则为此状态，说明该订单暂不可用
	public static final int LOCAL_STATUS_APPROVING = 0; // 未审核
	public static final int LOCAL_STATUS_APPROVED = 1; // 已审核后即为待发货
	public static final int LOCAL_STATUS_HANGED = 2; // 挂起1
	public static final int LOCAL_STATUS_HANGED_2 = 22; // 挂起
	public static final int LOCAL_STATUS_SENT = 3; // 已发货
	public static final int LOCAL_STATUS_COMPLETED = 4; // 已完成
	public static final int LOCAL_STATUS_DISABLE = 7; // 已作废
	public static final int LOCAL_STATUS_CANCELLED = 5;// 订单取消
	public static final int LOCAL_STATUS_HALT = 6;// 系统暂停订单
	// 本地状态针对京东
	public static final int LOCAL_STATUS_SENTJD = 8; // 发往京东
	public static final int LOCAL_STATUS_JDRECEIV = 10; // 京东已收货
	public static final int LOCAL_STATUS_REEJECTE = 11; // 买家拒收
	
	public static final int LOCAL_STATUS_MARGIN = 9;// 合并订单
	
	public static final int MERGE_STATUS_MAIN = 0; // 合并订单状态，主订单
	public static final int MERGE_STATUS_PART = 1; // 合并订单状态,被合并的订单
	
	public static final int DATA_STATUS_FINISH_NO = 0;
	public static final int DATA_STATUS_FINISH_YES = -1;
	
	public static final int HAS_REFUND_YES = 1; // 订单有退款申请
	public static final int HAS_REFUND_NO = 0; // 订单没有有退款申请
	
	public static final int WMS_STATUS_HAS_SENT_FAILED = 3; // WMS创建单失败
	public static final int WMS_STATUS_HAS_SENT_SUCCESS = 2; // WMS创建单成功
	public static final int WMS_STATUS_HAS_SENT = 1; // WMS已创建单
	public static final int WMS_STATUS_NO_SENT = 0; // WMS未创建单
	
	// 淘宝订单交易类型
	/**
	 * 淘宝订单交易类型： 一口价
	 */
	public final static String TYPE_FIXED = "fixed"; // 一口价
	/**
	 * 淘宝订单交易类型： 拍卖
	 */
	public final static String TYPE_AUCTION = "auction"; // 拍卖
	/**
	 * 淘宝订单交易类型： 一口价、拍卖
	 */
	public final static String TYPE_GUARANTEE_TRADE = "guarantee_trade"; // (一口价、拍卖)
	/**
	 * 淘宝订单交易类型： 自动发货
	 */
	public final static String TYPE_AUTO_DELIVERY = "auto_delivery"; // (自动发货)
	/**
	 * 淘宝订单交易类型： 旺店入门版交易
	 */
	public final static String TYPE_SIMPLE_TRADE = "independent_simple_trade"; // (旺店入门版交易)
	/**
	 * 淘宝订单交易类型： 直冲
	 */
	public final static String TYPE_EC = "ec"; // 直冲
	/**
	 * 淘宝订单交易类型： 货到付款
	 */
	public final static String TYPE_COD = "cod"; // 货到付款
	/**
	 * 淘宝订单交易类型： (分销)
	 */
	public final static String TYPE_FENXIAO = "fenxiao"; // (分销)
	/**
	 * 淘宝订单交易类型： 一口价
	 */
	public final static String TYPE_GAME_EQUIPMENT = "game_equipment"; // 游戏装备
	/**
	 * 淘宝订单交易类型： ShopEX交易
	 */
	public final static String TYPE_SHOPEX_TRADE = "shopex_trade"; // ShopEX交易
	/**
	 * 淘宝订单交易类型： 万网交易
	 */
	public final static String TYPE_NETCN_TRADE = "netcn_trade"; // 万网交易
	/**
	 * 淘宝订单交易类型： 统一外部交易
	 */
	public final static String TYPE_EXTRNAL_TRADE = "external_trade"; // 统一外部交易
	/**
	 * 淘宝订单交易类型： 万人团
	 */
	public final static String TYPE_STEP = "step"; // 万人团
	
	/**
	 * taobao.trades.sold.get 可以返回的属性列表
	 */
	// public final static String FIELDS_GET_SOLD_TRADES =
	// "tid,buyer_nick,type,payment,post_fee,discount_fee,adjust_fee,num,shipping_type,buyer_obtain_point_fee,point_fee,real_point_fee,total_fee,"
	// +
	// "receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,created,pay_time,"
	// +
	// "status,modified,end_time,consign_time,commission_fee,received_payment,is_lgtype,is_brand_sale,credit_card_fee,mark_desc,"
	// +
	// "orders.oid,orders.num_iid,orders.title,orders.price,orders.num,orders.total_fee, orders.discount_fee,orders.adjust_fee, orders.payment, orders.sku_id,"
	// +
	// "orders.sku_properties_name, orders.item_meal_name,orders.refund_status, orders.refund_id, orders.outer_iid, orders.outer_sku_id,orders.is_oversold ,orders.order_from,step_trade_status,step_paid_fee";
	//	
	
	public final static String FIELDS_GET_SOLD_TRADES = "seller_nick,buyer_nick,title,type,created,tid,seller_rate,seller_can_rate,buyer_rate,can_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,seller_flag,alipay_id,alipay_no,is_lgtype,is_force_wlb,is_brand_sale,buyer_area,has_buyer_message,credit_card_fee,lg_aging_type,lg_aging,step_trade_status,step_paid_fee,mark_desc,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,send_time,is_daixiao,is_wt,is_part_consign,zero_purchaseseller_nick,buyer_nick,title,type,created,tid,seller_rate,seller_can_rate,buyer_rate,can_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,seller_flag,alipay_id,alipay_no,is_lgtype,is_force_wlb,is_brand_sale,buyer_area,has_buyer_message,credit_card_fee,lg_aging_type,lg_aging,step_trade_status,step_paid_fee,mark_desc,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,send_time,is_daixiao,is_wt,is_part_consign,zero_purchase,orders,service_orders";
	
	/**
	 * excel标题头
	 */
	public final static String[] TRADE_TITLE = new String[] { "交易编号", "交易标题", "交易创建时间", "付款时间", "商品数字编号", "商品价格",
			"商品购买数量", "商品金额（商品价格乘以数量的总金额）", "买家昵称", "买家备注", "交易备注", "系统核对时间" };
	/**
	 * excel标题头
	 */
	public final static String[] TRADE_FIELD_NAME = new String[] { "tid", "title", "created", "payTime", "numIid",
			"price", "num", "totalFee", "buyerNick", "buyerMemo", "trade_memo", "updateTime" };
	
	/**
	 * 交易订单成功的状态交易状态，默认查询所有交易状态的数据，除了默认值外每次只能查询一种状态。 可选值：
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) SELLER_CONSIGNED_PART（卖家部分发货）
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功) TRADE_CLOSED(交易关闭)
	 * TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭)
	 * ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY)
	 * ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
	 * WAIT_PRE_AUTH_CONFIRM(余额宝0元购合约中)
	 */
	public final static String[] TRADE_STATUS = new String[] { "WAIT_SELLER_SEND_GOODS", "SELLER_CONSIGNED_PART",
			"WAIT_BUYER_CONFIRM_GOODS", "TRADE_BUYER_SIGNED", "TRADE_FINISHED" };
	
	/**
	 * 交易订单失败或等待买家的状态交易状态，默认查询所有交易状态的数据，除了默认值外每次只能查询一种状态。 可选值：
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) SELLER_CONSIGNED_PART（卖家部分发货）
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功) TRADE_CLOSED(交易关闭)
	 * TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭)
	 * ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY)
	 * ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
	 */
	public final static String[] TRADE_NO_PAY_STATUS = new String[] { "WAIT_BUYER_PAY", "TRADE_NO_CREATE_PAY",
			"ALL_WAIT_PAY" };
}
