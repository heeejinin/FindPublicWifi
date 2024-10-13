package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkGroupDTO {
    private int bmrkId;
    private String bmrkNm;
    private int seq;
    private String regDttm;
    private String editDttm;

}
