package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private int id;
    private int bmrkId;
    private String wifiNo;
    private String bmrkNm;
    private String wifiNm;
    private String regDttm;

}
