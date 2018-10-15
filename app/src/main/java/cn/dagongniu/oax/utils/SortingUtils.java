package cn.dagongniu.oax.utils;


import java.math.BigDecimal;
import java.text.Collator;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;

/**
 * 排序
 */
public class SortingUtils {


    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 时间排序
     *
     * @param mList
     */
    public static List<PropertyRechargeBean.DataBean.ListBean> sortDataShang(List<PropertyRechargeBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyRechargeBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyRechargeBean.DataBean.ListBean lhs, PropertyRechargeBean.DataBean.ListBean rhs) {
                Date date1 = SortingUtils.stringToDate(lhs.getCreateTime());
                Date date2 = SortingUtils.stringToDate(rhs.getCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    /**
     * 时间排序
     *
     * @param mList
     */
    public static List<PropertyWithdrawBean.DataBean.ListBean> sortDataShangW(List<PropertyWithdrawBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyWithdrawBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyWithdrawBean.DataBean.ListBean lhs, PropertyWithdrawBean.DataBean.ListBean rhs) {
                Date date1 = SortingUtils.stringToDate(lhs.getCreateTime());
                Date date2 = SortingUtils.stringToDate(rhs.getCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    /**
     * 时间排序
     *
     * @param mList
     */
    public static List<PropertyRechargeBean.DataBean.ListBean> sortDataXia(List<PropertyRechargeBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyRechargeBean.DataBean.ListBean>() {
            @Override
            public int compare(PropertyRechargeBean.DataBean.ListBean lhs, PropertyRechargeBean.DataBean.ListBean rhs) {
                Date date1 = SortingUtils.stringToDate(lhs.getCreateTime());
                Date date2 = SortingUtils.stringToDate(rhs.getCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date2.before(date1)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    /**
     * 时间排序
     *
     * @param mList
     */
    public static List<PropertyWithdrawBean.DataBean.ListBean> sortDataXiaW(List<PropertyWithdrawBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyWithdrawBean.DataBean.ListBean>() {
            @Override
            public int compare(PropertyWithdrawBean.DataBean.ListBean lhs, PropertyWithdrawBean.DataBean.ListBean rhs) {
                Date date1 = SortingUtils.stringToDate(lhs.getCreateTime());
                Date date2 = SortingUtils.stringToDate(rhs.getCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date2.before(date1)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }


    /**
     * 数字排序
     *
     * @param mList
     */
    public static List<PropertyRechargeBean.DataBean.ListBean> sortCountShang(List<PropertyRechargeBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyRechargeBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyRechargeBean.DataBean.ListBean lhs, PropertyRechargeBean.DataBean.ListBean rhs) {
                BigDecimal date1 = lhs.getQty();
                BigDecimal date2 = rhs.getQty();
                // 对日期字段进行升序，如果欲降序可采用after方法
                switch (date1.compareTo(date2)) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        return mList;
    }

    /**
     * 数字排序
     *
     * @param mList
     */
    public static List<PropertyWithdrawBean.DataBean.ListBean> sortCountShangW(List<PropertyWithdrawBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyWithdrawBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyWithdrawBean.DataBean.ListBean lhs, PropertyWithdrawBean.DataBean.ListBean rhs) {
                BigDecimal date1 = lhs.getQty();
                BigDecimal date2 = rhs.getQty();
                // 对日期字段进行升序，如果欲降序可采用after方法
                switch (date1.compareTo(date2)) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        return mList;
    }


    /**
     * 数字排序
     *
     * @param mList
     */
    public static List<PropertyRechargeBean.DataBean.ListBean> sortCountXia(List<PropertyRechargeBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyRechargeBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyRechargeBean.DataBean.ListBean lhs, PropertyRechargeBean.DataBean.ListBean rhs) {
                BigDecimal date1 = lhs.getQty();
                BigDecimal date2 = rhs.getQty();
                // 对日期字段进行升序，如果欲降序可采用after方法
                switch (date2.compareTo(date1)) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        return mList;
    }

    /**
     * 数字排序
     *
     * @param mList
     */
    public static List<PropertyWithdrawBean.DataBean.ListBean> sortCountXiaW(List<PropertyWithdrawBean.DataBean.ListBean> mList) {
        Collections.sort(mList, new Comparator<PropertyWithdrawBean.DataBean.ListBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(PropertyWithdrawBean.DataBean.ListBean lhs, PropertyWithdrawBean.DataBean.ListBean rhs) {
                BigDecimal date1 = lhs.getQty();
                BigDecimal date2 = rhs.getQty();
                // 对日期字段进行升序，如果欲降序可采用after方法
                switch (date2.compareTo(date1)) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        return mList;
    }


    /**
     * 充值记录 A-Z 排序
     */
    public static List<PropertyRechargeBean.DataBean.ListBean> AZRechargeSorting(List<PropertyRechargeBean.DataBean.ListBean> beanList) {

        List<PropertyRechargeBean.DataBean.ListBean> listtoSort = new ArrayList<>();
        listtoSort.addAll(beanList);
        if (!listtoSort.isEmpty()) {    //不为空

            Comparator<PropertyRechargeBean.DataBean.ListBean> comparator = new Comparator<PropertyRechargeBean.DataBean.ListBean>() {

                @Override
                public int compare(PropertyRechargeBean.DataBean.ListBean o1, PropertyRechargeBean.DataBean.ListBean o2) {
                    Collator collator = Collator.getInstance();
                    return collator.getCollationKey(o1.getShortName()).compareTo(
                            collator.getCollationKey(o2.getShortName()));
                }
            };
            Collections.sort(listtoSort, comparator);
        }
        return listtoSort;
    }

    /**
     * 提现记录 A-Z 排序
     */
    public static List<PropertyWithdrawBean.DataBean.ListBean> AZWithdrawSorting(List<PropertyWithdrawBean.DataBean.ListBean> beanList) {

        List<PropertyWithdrawBean.DataBean.ListBean> listtoSort = new ArrayList<>();
        listtoSort.addAll(beanList);
        if (!listtoSort.isEmpty()) {    //不为空

            Comparator<PropertyWithdrawBean.DataBean.ListBean> comparator = new Comparator<PropertyWithdrawBean.DataBean.ListBean>() {

                @Override
                public int compare(PropertyWithdrawBean.DataBean.ListBean o1, PropertyWithdrawBean.DataBean.ListBean o2) {
                    Collator collator = Collator.getInstance();
                    return collator.getCollationKey(o1.getShortName()).compareTo(
                            collator.getCollationKey(o2.getShortName()));
                }
            };
            Collections.sort(listtoSort, comparator);
        }
        return listtoSort;
    }

    /**
     * 遍历Map
     *
     * @param data
     * @return
     */
    public static String[] sortAscending(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        Comparator<Object> comparator = Collator.getInstance(java.util.Locale.ENGLISH);
        Arrays.sort(data, comparator);
        return data;
    }

    /**
     * A-Z 排序
     */
    public static List<CoinListBean.DataBean> AZSorting(List<CoinListBean.DataBean> beanList) {

        if (!beanList.isEmpty()) {    //不为空

            Comparator<CoinListBean.DataBean> comparator = new Comparator<CoinListBean.DataBean>() {

                @Override
                public int compare(CoinListBean.DataBean o1, CoinListBean.DataBean o2) {
                    Collator collator = Collator.getInstance();
                    return collator.getCollationKey(o1.getCoinName()).compareTo(
                            collator.getCollationKey(o2.getCoinName()));
                }
            };
            Collections.sort(beanList, comparator);
        }
        return beanList;
    }
}
