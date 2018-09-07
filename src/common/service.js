/**
 * Created by qiaozm on 2017/04/07
 * 接口地址配置模块
 */
import React from 'react-native';
//const serviceName="https://ssl.ygzykj.com:221/sunsoft-supplier-app/";
// const serviceName="http://192.168.1.38:8080/sunsoft-supplier-app/";
//const serviceName="http://192.168.1.37:8082/sunsoft-supplier-app/";
//const serviceName="https://ssl.ygzykj.com:231/sunsoft-supplier-app/";
const serviceName = "";
module.exports = {
    /*******************************公共接口**************************************************/
    selectSchoolInfoAndClassInfo: serviceName + 'school/selectSchoolInfoAndClassInfo.json',
    /********************************首页接口********************************************/
    //首页轮播图接口地址
    supplierSlidead: serviceName + 'supplierMain/supplierSlidead.json',
    /********************************服务中学校接口********************************************/
    //服务中学校列表接口地址
    selectListBySchoolName: serviceName + 'school/selectListBySchoolName.json',
    //服务中学校详情接口（根据学校ID 查询 学校管理员名称和手机号、 学校名称、学校校徽、学校地址、省市区名称、年级数、班级数、学校人数）
    selectschoolIdBySchoolDetails: serviceName + 'school/selectschoolIdBySchoolDetails.json',
    //根据学校ID、年级编号 查询相应年级的班级人数
    supplierSelectSchoolIdByschoolClassNumber: serviceName + 'school/supplierSelectSchoolIdByschoolClassNumber.json',
    //获取所属区域
    selectSupplierRegion: serviceName + 'supplierRegion/selectSupplierRegion1_1_3.json',//1.1.3版本调用新接口
//    selectSupplierRegion:serviceName+'supplierRegion/selectSupplierRegion.json',//1.1.3之前版本接口

    /********************************历史服务学校接口********************************************/
    //历史服务学校列表接口地址
    getHistoryServiceSchoolList: serviceName + 'school/getHistoryServiceSchoolList.json',
    //历史服务学校列表筛选条件-查询指定年学年列表
    //getSemesterList:serviceName+'schoolyear/getSemesterList.json',
    getSemesterList: serviceName + 'schoolyear/getSemesterStartAndEndTimeList.json',
    //历史服务学校列表筛选条件-获取学期具体时间段
    getTimeBySemester: serviceName + 'schoolyear/getTimeBySemester.json',
    //历史服务学校详情-查询历史服务学校成交金额
    getHSchoolTradingRecord: serviceName + 'school/getHSchoolTradingRecord.json',
    //历史服务学校详情-根据学校ID查询学校厂商合作信息
    getTeamworkBySchoolId: serviceName + 'school/getTeamworkBySchoolId.json',

    /********************************公告期内学校列表接口********************************************/
    //公告期内学校列表接口地址
    getInReleaseNoticeSchoolList: serviceName + 'school/getInReleaseNoticeSchoolList.json',
    //公告期内学校列表筛选条件-时间查询
    getInValidaFiltrate: serviceName + 'school/getInValidaFiltrate.json',
    //公告期内学校详情接口地址
    getInReleaseNoticeListDetail: serviceName + 'school/getInReleaseNoticeListDetail.json',
    //公告期内公告内容接口地址
    getInReleaseNoticeDetailByNoticeId: serviceName + 'school/getInReleaseNoticeDetailByNoticeId.json',

    /********************************公告期内订购查询接口********************************************/
    //查询公告期内订购列表
    getInReleaseNoticeOrderList: serviceName + 'supplierNotice/getInReleaseNoticeOrderList.json',
    //查询公告期内订购详情
    getInReleaseNoticeOrderDetail: serviceName + 'supplierNotice/getInReleaseNoticeOrderDetail.json',
    //查询公告期内订购详情班级年级
    getSingleGrade: serviceName + 'supplierNotice/getSingleGradeOrderInfo.json',
    //查询公告期内订购公告名称和男女款
    getNoticeOrdeInfoByNoticeId: serviceName + 'supplierNotice/getNoticeOrdeInfoByNoticeId.json',


    /********************************公告结束订单查询接口********************************************/
    //查询公告结束订购列表
    getFinishNoticeOrderList: serviceName + 'supplierNotice/getFinishNoticeOrderList.json',
    //查询历史统购期公告详情-公告列表
    getFinishNoticeOrderDetail: serviceName + 'supplierNotice/getFinishNoticeOrderDetail.json',
    //查询结束订单详情
    getFinishNoticeGradeOrderInfo: serviceName + 'supplierNotice/getFinishNoticeGradeOrderInfo.json',
    //查询结束订单详情-各班级订购情况
    getFinishNoticeClassOrderInfo: serviceName + 'supplierNotice/getFinishNoticeClassOrderInfo.json',

    /********************************货款查询接口********************************************/
    //货款学校列表
    getSchoolIdByLoanSchoolsList: serviceName + 'sunSettlementBill/getSchoolIdByLoanSchoolsList.json',
    //货款详情
    getSchoolIdByLoanSchoolsDetails: serviceName + 'sunSettlementBill/getSchoolIdByLoanSchoolsDetails.json',

    /********************************零售查询接口********************************************/
    //零售学校列表
    selectSchoolIdAndSchoolInfo: serviceName + 'school/selectSchoolIdAndSchoolInfo.json',
    //零售详情-商品列表
    getSchoolIdByRetaillGoodsList: serviceName + 'retail/getSchoolIdByRetaillGoodsList.json',

    /********************************数据统计接口********************************************/
    getUserIdAndSupplierIdByYEARAndMONTH: serviceName + 'supplierDataStatistics/getUserIdAndSupplierIdByYEARAndMONTH.json',
    getEachMonthSchoolAndNoticeNumber: serviceName + 'supplierDataStatistics/getEachMonthSchoolAndNoticeNumber.json',


    baseUrl: "https://test.ygzykj.com:1701/sunsoft-supplier-app/",
    // baseUrl: "http://192.168.11.95:8080/sunsoft-supplier-app/",

//    uat环境
//        public static String BASE_URL = "https://ssl.ygzykj.com:8480/sunsoft-supplier-app/";
//发布环境
//    public static String BASE_URL = "https://mcs.ygzykj.com:6570/sunsoft-supplier-app/";
//    public static String BASE_URL = "https://supplier.ygzykj.com/sunsoft-supplier-app/";
    /*--------闪屏页接口--------*/
    /*获取服务器地址和app是否更新*/
    checkVersion: "sunVersion/checkVersion.json",
    /*bundle更新*/
    bundleVersionUrl: "sunVersion/bundleVersionUrl.json",

    /*-----登录接口----*/
    /*登录*/
    login: "login/login.json",
    /*图片验证码*/
    VERIFICATION_CODE: "login/validatecode.json",
};