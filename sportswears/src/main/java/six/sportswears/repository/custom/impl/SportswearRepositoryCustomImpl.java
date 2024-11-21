package six.sportswears.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.*;
import six.sportswears.repository.custom.SportswearRepositoryCustom;
import six.sportswears.utils.StringUtils;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SportswearRepositoryCustomImpl implements SportswearRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public static void joinTable(SearchRequest searchRequest, StringBuilder sql) {
        String category = searchRequest.getCategoryName();
        if (StringUtils.check(category)) {
            sql.append(" INNER JOIN sportswear_category on b.sportswear_category_id = sportswear_category.id ");
        }
    }

    public static void querySpecial(SearchRequest searchRequest, StringBuilder where) {
        String category = searchRequest.getCategoryName();
        if (StringUtils.check(category)) {
            where.append(" AND sportswear_category.name LIKE '%").append(category).append("%' ");
        }
    }

    public static void queryNormal(SearchRequest searchRequest, StringBuilder where) {
        String name = searchRequest.getKey();
        if (StringUtils.check(name)) {
            where.append(" AND b.name LIKE '%").append(searchRequest.getKey()).append("%' ");
        }


    }

    @Override
    public List<Sportswear> findAllSportswearBySearchForCustomer(SearchRequest searchRequest) {
        StringBuilder sql = new StringBuilder("SELECT b.*  FROM sportswear b ");
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        joinTable(searchRequest, sql);
        queryNormal(searchRequest, where);
        querySpecial(searchRequest, where);
        where.append(" AND b.status = 1 ");
        where.append(" ORDER BY RAND(); ");
//        where.append(" GROUP BY b.id ");
        sql.append(where);
        Query query = entityManager.createNativeQuery(sql.toString(), Sportswear.class);
        return query.getResultList();
    }

    @Override
    public List<Sportswear> findAllSportswearBySearchForAdmin(SearchRequest searchRequest) {
        StringBuilder sql = new StringBuilder("SELECT b.*  FROM sportswear b ");
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        joinTable(searchRequest, sql);
        queryNormal(searchRequest, where);
        querySpecial(searchRequest, where);

        where.append(" GROUP BY b.id ");
        sql.append(where);
        Query query = entityManager.createNativeQuery(sql.toString(), Sportswear.class);
        return query.getResultList();
    }

    @Override
    public SportswearRevenueResponse getSportswearRevenueByIDAndTime(Long ID, String time) {
        StringBuilder sql = new StringBuilder("SELECT SUM(order_item.total_price) AS revenue, " +
                "       SUM(order_item.quantity_ordered) AS  quantity " +
                " FROM order_item " +
                "INNER JOIN orders  ON order_item.orders_id = orders.id WHERE order_item.sportswear_id = ");
        sql.append(ID.toString());
        sql.append(" ").append(" AND MONTH(orders.order_date) = ").append(time);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString());

        Object[] results = (Object[]) query.getSingleResult();

        SportswearRevenueResponse response = new SportswearRevenueResponse();
        response.setRevenue((Double) results[0]);
        response.setQuantity(((BigDecimal) results[1]).longValue());

        return response;
    }

    @Override
    public ListSportswearRevenueResponseInAMonth getAllSportswearRevenueByTime(Integer time) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "    MONTH(o.order_date) AS month,\n" +
                "    SUM(oi.total_price) AS total_revenue,\n" +
                "    SUM(oi.quantity_ordered) AS total_quantity\n" +
                "FROM order_item oi\n" +
                "INNER JOIN orders o ON oi.orders_id = o.id\n" +
                "WHERE YEAR(o.order_date) = ? \n" +
                "GROUP BY MONTH(o.order_date)\n" +
                "ORDER BY MONTH(o.order_date);");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, time);
        List<Object[]> resultList = query.getResultList();
        List<SportswearRevenueResponseInAMonth> sportswearRevenueResponseInAMonthList = new ArrayList<>();
        for (Object[] result : resultList) {
            SportswearRevenueResponseInAMonth sportswearRevenueResponseInAMonth = new SportswearRevenueResponseInAMonth();
            sportswearRevenueResponseInAMonth.setMonth((Integer) result[0]);
            sportswearRevenueResponseInAMonth.setRevenue((Double) result[1]);
            sportswearRevenueResponseInAMonth.setQuantity(((BigDecimal) result[2]).longValue());
            sportswearRevenueResponseInAMonthList.add(sportswearRevenueResponseInAMonth);
        }
        ListSportswearRevenueResponseInAMonth listSportswearRevenueResponseInAMonth = new ListSportswearRevenueResponseInAMonth();
        listSportswearRevenueResponseInAMonth.setSportswearRevenueResponseInAMonthList(sportswearRevenueResponseInAMonthList);
        listSportswearRevenueResponseInAMonth.setYear(time);
        return listSportswearRevenueResponseInAMonth;

    }

    @Override
    public ListSportswearRevenueResponseInAMonth getListSportswearRevenueResponseInAMonth(Long id, Integer year) {
        StringBuilder sql = new StringBuilder("SELECT \n" +
                "    MONTH(o.order_date) AS month,\n" +
                "    SUM(oi.total_price) AS total_revenue,\n" +
                "    SUM(oi.quantity_ordered) AS total_quantity\n" +
                "FROM \n" +
                "    order_item oi\n" +
                "INNER JOIN \n" +
                "    orders o ON oi.orders_id = o.id\n" +
                "WHERE \n" +
                "    oi.sportswear_id = ? \n" +
                "    AND YEAR(o.order_date) = ?\n" +
                "GROUP BY \n" +
                "    MONTH(o.order_date)\n" +
                "ORDER BY \n" +
                "    MONTH(o.order_date);");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, id);
        query.setParameter(2, year);
        List<Object[]> resultList = query.getResultList();
        List<SportswearRevenueResponseInAMonth> sportswearRevenueResponseInAMonthList = new ArrayList<>();
        for (Object[] result : resultList) {
            SportswearRevenueResponseInAMonth sportswearRevenueResponseInAMonth = new SportswearRevenueResponseInAMonth();
            sportswearRevenueResponseInAMonth.setMonth((Integer) result[0]);
            sportswearRevenueResponseInAMonth.setRevenue((Double) result[1]);
            sportswearRevenueResponseInAMonth.setQuantity(((BigDecimal) result[2]).longValue());
            sportswearRevenueResponseInAMonthList.add(sportswearRevenueResponseInAMonth);
        }
        ListSportswearRevenueResponseInAMonth listSportswearRevenueResponseInAMonth = new ListSportswearRevenueResponseInAMonth();
        listSportswearRevenueResponseInAMonth.setSportswearRevenueResponseInAMonthList(sportswearRevenueResponseInAMonthList);
        listSportswearRevenueResponseInAMonth.setYear(year);
        return listSportswearRevenueResponseInAMonth;
    }

    @Override
    public ListLabelAndValue getListLabelAndValue(String month , Integer year) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "    sc.name,\n" +
                "    SUM(oi.quantity_ordered) AS total_quantity\n" +
                "FROM\n" +
                "    sportswear_category sc\n" +
                "INNER JOIN sportswear sw ON sc.id = sw.sportswear_category_id\n" +
                "INNER JOIN order_item oi ON sw.id = oi.sportswear_id\n" +
                "INNER JOIN orders o ON oi.orders_id = o.id\n" +
                "WHERE\n" +
                "     MONTH(o.order_date) = ? AND YEAR(o.order_date) = ? \n" +
                "GROUP BY\n" +
                "    sc.id;");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, month);
        query.setParameter(2, year);
        List<Object[]> resultList = query.getResultList();
        List<LabelAndValue> labelAndValueList = new ArrayList<>();
        for (Object[] result : resultList) {
            LabelAndValue labelAndValue = new LabelAndValue();
            labelAndValue.setLabel((String) result[0]);

            labelAndValue.setValue(((BigDecimal) result[1]).longValue());
            labelAndValueList.add(labelAndValue);
        }
        ListLabelAndValue listLabelAndValue = new ListLabelAndValue();
        listLabelAndValue.setLabelAndValueList(labelAndValueList);
        listLabelAndValue.setMonth(month);
        return listLabelAndValue;
    }
}
