package model;

public class Order {
	private String shoplinker_order_id ;	//����Ŀ �ֹ���ȣ 	
	private String mall_order_id; //���θ� �ֹ���ȣ 		
	private String order_name;  //�ֹ��ڸ� 
	private String order_tel;  //�ֹ��� ��ȭ��ȣ    
	private String order_cel;  //�ֹ��� �ڵ�����ȣ
	private String receive;  // �����θ�   
	
	private String receive_tel;  //������ ��ȭ��ȣ   
	private String receive_cel; //������ �ڵ�����ȣ   
	private String receive_zipcode; //������ �����ȣ  
	private String receive_addr; //������ �ּ� 
	private String baesong_bi;  //��ۺ�
	private String delivery_msg;  //��۸޼���
	private String order_product_id; //��ǰ��ȣ
	private String product_name; //��ǰ��
	private String quantity;  //���ֹ�����
	private String sale_price; //���ֹ��ݾ�
	private String orderdate; //�ֹ�����
	
	   
	public String get_shoplinker_order_id () {
	    return shoplinker_order_id ;
	}
	public void set_shoplinker_order_id (String shoplinker_order_id ) {
	    this.shoplinker_order_id  = shoplinker_order_id ;
	}
	
	public String get_mall_order_id () {
	    return mall_order_id ;
	}
	public void set_mall_order_id (String mall_order_id ) {
	    this.mall_order_id  = mall_order_id ;
	}
	public String get_order_name () {
	    return order_name ;
	}
	public void set_order_name (String order_name ) {
	    this.mall_order_id  = order_name ;
	}
	public String get_order_tel() {
	    return order_tel ;
	}
	public void set_order_tel(String order_tel ) {
	    this.order_tel  = order_tel ;
	}
	public String get_order_cel() {
	    return order_cel ;
	}
	public void set_order_cel(String order_cel ) {
	    this.order_cel  = order_cel ;
	}
	
	public String get_receive() {
	    return  receive   ;
	}
	public void set_receive(String  receive) {
	    this.receive    =  receive;
	}
	
	public String get_receive_tel() {
	    return  receive_tel   ;
	}
	public void set_receive_tel(String  receive_tel) {
	    this.receive_tel    =  receive_tel;
	}
	public String get_receive_cel() {
	    return  receive_cel   ;
	}
	public void set_receive_cel(String  receive_cel) {
	    this.receive_cel    =  receive_cel;
	}
	public String get_receive_zipcode() {
	    return  receive_zipcode   ;
	}
	public void set_receive_zipcode(String  receive_zipcode) {
	    this.receive_zipcode    =  receive_zipcode;
	}
	public String get_receive_addr() {
	    return  receive_addr   ;
	}
	public void set_receive_addr(String  receive_addr) {
	    this.receive_addr    =  receive_addr;
	}
	public String get_baesong_bi() {
	    return  baesong_bi   ;
	}
	public void set_baesong_bi(String  baesong_bi) {
	    this.baesong_bi    =  baesong_bi;
	}
	
	public String get_delivery_msg() {
	    return  delivery_msg   ;
	}
	public void set_delivery_msg(String  delivery_msg) {
	    this.delivery_msg    =  delivery_msg;
	}
	public String get_order_product_id() {
	    return  order_product_id   ;
	}
	public void set_order_product_id(String  order_product_id) {
	    this.order_product_id    =  order_product_id;
	}
	public String get_product_name() {
	    return  product_name   ;
	}
	public void set_product_name(String  product_name) {
	    this.product_name    =  product_name;
	}
	public String get_quantity() {
	    return  quantity   ;
	}
	public void set_quantity(String  quantity) {
	    this.quantity    =  quantity;
	}
	public String get_sale_price() {
	    return  sale_price   ;
	}
	public void set_sale_price(String  sale_price) {
	    this.sale_price    =  sale_price;
	}
	public String get_orderdate() {
	    return  orderdate   ;
	}
	public void set_orderdate(String  orderdate) {
	    this.orderdate    =  orderdate;
	}
}
