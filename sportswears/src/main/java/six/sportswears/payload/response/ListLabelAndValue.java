package six.sportswears.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListLabelAndValue {
    String month;
    List<LabelAndValue> labelAndValueList;
}
