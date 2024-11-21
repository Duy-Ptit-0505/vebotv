package six.sportswears.repository.custom;



import org.springframework.stereotype.Repository;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.ListLabelAndValue;
import six.sportswears.payload.response.ListSportswearRevenueResponseInAMonth;
import six.sportswears.payload.response.SportswearRevenueResponse;
import six.sportswears.payload.response.SportswearRevenueResponseInAMonth;

import java.util.List;
@Repository

public interface SportswearRepositoryCustom {
    List<Sportswear> findAllSportswearBySearchForCustomer(SearchRequest searchRequest);

    List<Sportswear> findAllSportswearBySearchForAdmin(SearchRequest searchRequest);

    SportswearRevenueResponse getSportswearRevenueByIDAndTime(Long ID, String time);

    ListSportswearRevenueResponseInAMonth getAllSportswearRevenueByTime(Integer time);

    ListSportswearRevenueResponseInAMonth getListSportswearRevenueResponseInAMonth(Long id, Integer Year);

    ListLabelAndValue getListLabelAndValue(String month, Integer Year);
}
