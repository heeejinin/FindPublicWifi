package dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO{
    private int historyId; // id
    private String lat; // x좌표
    private String lnt; // y좌표
    private String viewDttm;// 조회일자
}